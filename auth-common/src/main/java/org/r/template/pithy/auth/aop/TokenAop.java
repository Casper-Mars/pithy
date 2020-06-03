package org.r.template.pithy.auth.aop;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.r.template.pithy.auth.annotation.Token;
import org.r.template.pithy.auth.service.JwtService;
import org.r.template.pithy.commom.enums.ResultCodeEnum;
import org.r.template.pithy.commom.rpc.ResultDto;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * date 2020/6/3 下午5:07
 *
 * @author casper
 **/
@Aspect
public class TokenAop {

    private final JwtService jwtService;

    public TokenAop(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Pointcut(value = "execution(public * org.r.template.pithy.*.api.*.*(..))")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResultDto.<String>error(ResultCodeEnum.AUTH_NOT_PERMIT);
        }
        Claims claims = jwtService.parseJWT(token);
        if (claims == null) {
            return ResultDto.<String>error(ResultCodeEnum.AUTH_NOT_PERMIT);
        }
        Object[] args = joinPoint.getArgs();
        try {
            //获取方法，此处可将signature强转为MethodSignature
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int tokenAnnotation = findTokenAnnotation(parameterAnnotations);
            if (tokenAnnotation != -1) {
                Object[] tmp = args;
                int j = 0;
                args = new Object[joinPoint.getArgs().length + 1];
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    if (tokenAnnotation == i) {
                        args[i] = claims;
                        j += 2;
                    } else {
                        args[i] = tmp[j++];
                    }
                }
            }
        } catch (Exception e) {
            return ResultDto.<String>error(ResultCodeEnum.INTERNAL_ERROR);
        }
        return joinPoint.proceed(args);
    }

    /**
     * 寻找带有Token注解的参数位置，找的到则返回参数的位置，否则返回-1
     *
     * @param parameterAnnotations 参数列表
     * @return
     */
    private int findTokenAnnotation(Annotation[][] parameterAnnotations) {
        if (parameterAnnotations.length > 0) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                if (parameterAnnotations[i].length > 0) {
                    for (Annotation annotation : parameterAnnotations[i]) {
                        if (annotation instanceof Token) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }


}

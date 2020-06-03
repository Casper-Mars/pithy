package org.r.template.pithy.auth.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
    public void pointCut(){

    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return ResultDto.<String>error(ResultCodeEnum.AUTH_NOT_PERMIT);
        }
        jwtService.parseJWT(token);



        Object[] args = joinPoint.getArgs();
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();





        return joinPoint.proceed();
    }







}

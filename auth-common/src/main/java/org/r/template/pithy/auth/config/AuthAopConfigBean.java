package org.r.template.pithy.auth.config;

/**
 * date 2020/6/3 下午5:35
 *
 * @author casper
 **/
public class AuthAopConfigBean {

    /**
     * 需要权限校验的路径
     */
    private String packagePath;

    /**
     * 开启
     */
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}

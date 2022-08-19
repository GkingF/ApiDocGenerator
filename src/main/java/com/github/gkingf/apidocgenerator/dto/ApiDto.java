package com.github.gkingf.apidocgenerator.dto;

public class ApiDto {
    /**
     * 注释
     */
    private String comment;
    /**
     * 上下文路径
     */
    private String contextPath;
    /**
     * 类URL
     */
    private String prefix;
    /**
     * 接口URL
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

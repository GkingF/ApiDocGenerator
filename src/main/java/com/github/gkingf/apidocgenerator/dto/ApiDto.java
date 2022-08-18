package com.github.gkingf.apidocgenerator.dto;

import com.github.gkingf.apidocgenerator.spring.SpringWebMappingAnnotation;

public class ApiDto {
    private String comment;
    private String prefix;
    private String url;
    private SpringWebMappingAnnotation method;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public SpringWebMappingAnnotation getMethod() {
        return method;
    }

    public void setMethod(SpringWebMappingAnnotation method) {
        this.method = method;
    }
}

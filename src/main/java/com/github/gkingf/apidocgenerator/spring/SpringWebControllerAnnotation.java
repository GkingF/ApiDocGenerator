package com.github.gkingf.apidocgenerator.spring;

public enum SpringWebControllerAnnotation {

    REST_CONTROLLER("org.springframework.web.bind.annotation.RestController");

    private final String qualifiedName;

    SpringWebControllerAnnotation(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }
}

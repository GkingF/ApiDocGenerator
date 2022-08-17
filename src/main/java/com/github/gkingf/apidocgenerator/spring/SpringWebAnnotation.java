package com.github.gkingf.apidocgenerator.spring;

public enum SpringWebAnnotation {
    REST_CONTROLLER("org.springframework.web.bind.annotation.RestController");

    private final String qualifiedName;

    SpringWebAnnotation(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }
}

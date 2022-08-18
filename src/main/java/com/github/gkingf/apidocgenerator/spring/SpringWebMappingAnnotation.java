package com.github.gkingf.apidocgenerator.spring;

public enum SpringWebMappingAnnotation {

    GET_MAPPING("org.springframework.web.bind.annotation.GetMapping"),
    POST_MAPPING("org.springframework.web.bind.annotation.PostMapping"),
    PUT_MAPPING("org.springframework.web.bind.annotation.PutMapping"),
    DELETE_MAPPING("org.springframework.web.bind.annotation.DeleteMapping"),
    PATCH_MAPPING("org.springframework.web.bind.annotation.PatchMapping");

    private final String qualifiedName;

    SpringWebMappingAnnotation(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }
}

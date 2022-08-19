package com.github.gkingf.apidocgenerator.spring;

public enum SpringWebMappingAnnotation {

    GET_MAPPING("GET", "org.springframework.web.bind.annotation.GetMapping"),
    POST_MAPPING("POST", "org.springframework.web.bind.annotation.PostMapping"),
    PUT_MAPPING("PUT", "org.springframework.web.bind.annotation.PutMapping"),
    DELETE_MAPPING("DELETE", "org.springframework.web.bind.annotation.DeleteMapping"),
    PATCH_MAPPING("PATCH", "org.springframework.web.bind.annotation.PatchMapping"),
    REQUEST_MAPPING("REQUEST", "org.springframework.web.bind.annotation.RequestMapping");

    private final String qualifiedName;
    private final String shortName;

    SpringWebMappingAnnotation(String shortName, String qualifiedName) {
        this.qualifiedName = qualifiedName;
        this.shortName = shortName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getShortName() {
        return shortName;
    }

    public static SpringWebMappingAnnotation byQualifiedName(String QualifiedName) {
        for (SpringWebMappingAnnotation anno : values()) {
            if (anno.getQualifiedName().equals(QualifiedName)) {
                return anno;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return shortName;
    }
}

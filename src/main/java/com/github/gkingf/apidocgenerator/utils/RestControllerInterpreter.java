package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.spring.SpringWebMappingAnnotation;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * psiJavaFile操作的简单封装，织入spring相关的操作
 */
public class RestControllerInterpreter {

    private final PsiJavaFile javaFile;
    private final PsiClass fileClass;
    private final Set<String> mappingAnnotationNames;

    private RestControllerInterpreter(PsiJavaFile javaFile) {
        this.javaFile = javaFile;
        this.fileClass = javaFile.getClasses()[0];
        mappingAnnotationNames = Arrays.stream(SpringWebMappingAnnotation.values())
                .map(SpringWebMappingAnnotation::getQualifiedName)
                .collect(Collectors.toSet());
    }

    public static RestControllerInterpreter getInstance(PsiJavaFile javaFile) {
        return new RestControllerInterpreter(javaFile);
    }

    /**
     * 获取类注释
     *
     * @return 注释文本
     */
    public String getClassComments() {
        return PsiJavaUtils.getCommentDescription(javaFile.getClasses()[0].getDocComment());
    }

    /**
     * 获取所有带有spring RequestMapping注解的方法
     *
     * @return 方法数组
     */
    public List<PsiMethod> getMappingMethods() {
        return Stream.of(fileClass.getMethods())
                .filter(m ->
                        Arrays.stream(m.getAnnotations())
                                .map(PsiAnnotation::getQualifiedName)
                                .anyMatch(mappingAnnotationNames::contains)
                )
                .collect(Collectors.toList());
    }

    /**
     * 获取方法描述
     *
     * @param method 目标方法
     */
    public String getMethodDescription(PsiMethod method) {
        return PsiJavaUtils.getCommentDescription(method.getDocComment());
    }
}

package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.dto.ApiDto;
import com.github.gkingf.apidocgenerator.spring.SpringWebMappingAnnotation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;

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
    private final PsiClass javaClass;
    private final Set<String> mappingAnnotationNames;
    private final Project project;
    private final Module module;

    private RestControllerInterpreter(PsiJavaFile javaFile) {
        this.project = javaFile.getProject();
        this.module = ModuleUtil.findModuleForPsiElement(javaFile);
        this.javaFile = javaFile;
        this.javaClass = javaFile.getClasses()[0];
        mappingAnnotationNames = Arrays.stream(SpringWebMappingAnnotation.values())
                .map(SpringWebMappingAnnotation::getQualifiedName)
                .collect(Collectors.toSet());
    }

    public static RestControllerInterpreter getInstance(PsiJavaFile javaFile) {
        return new RestControllerInterpreter(javaFile);
    }

    public List<ApiDto> generateApiDto() {

        String propValue = Arrays.stream(ArrayUtils.concatAll(
                        FilenameIndex.getFilesByName(project, "application.yml", GlobalSearchScope.moduleScope(module)),
                        FilenameIndex.getFilesByName(project, "application.yaml", GlobalSearchScope.moduleScope(module)),
                        FilenameIndex.getFilesByName(project, "bootstrap.yml", GlobalSearchScope.moduleScope(module)),
                        FilenameIndex.getFilesByName(project, "bootstrap.yaml", GlobalSearchScope.moduleScope(module))
                ))
                .map(f -> {
                    YamlParser yamlParser = new YamlParser(f);
                    String contextPart = yamlParser.findProperty("server", "servlet", "context-path");
                    if (StringUtils.isEmpty(contextPart)) {
                        contextPart = yamlParser.findProperty("server", "context-path");
                    }
                    return contextPart;
                })
                .filter(StringUtils::isNoneBlank)
                .findFirst()
                .orElse("");
        String contextPath = StringUtils.isBlank(propValue) ? propValue : (propValue.startsWith("/") ? propValue : "/" + propValue);
        String prefix = getRequestUrl(getMappingAnnotation(javaClass));

        return getMappingMethods().stream()
                .map(m -> {
                    PsiAnnotation mappingAnnotation = getMappingAnnotation(m);
                    ApiDto dto = new ApiDto();
                    dto.setComment(PsiJavaUtils.getCommentDescription(m.getDocComment()));
                    dto.setContextPath(contextPath);
                    dto.setPrefix(prefix);
                    dto.setUrl(getRequestUrl(mappingAnnotation));
                    dto.setMethod(getRequestMethod(mappingAnnotation));
                    return dto;
                })
                .collect(Collectors.toList());
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
     * 获取请求方式
     *
     * @param annotation 目标注解
     * @return 请求方式
     */
    private String getRequestMethod(PsiAnnotation annotation) {
        if (Objects.isNull(annotation)) {
            return "";
        }
        SpringWebMappingAnnotation mappingAnnotation = SpringWebMappingAnnotation.byQualifiedName(annotation.getQualifiedName());
        if (Objects.isNull(mappingAnnotation)) {
            return "";
        }
        if (SpringWebMappingAnnotation.REQUEST_MAPPING.equals(mappingAnnotation)) {
            PsiAnnotationMemberValue method = annotation.findAttributeValue("method");
            if (Objects.isNull(method)) {
                return "ALL";
            }
            return String.join(", ",
                    method.getText()
                            .replaceAll(" ", "")
                            .replaceAll("\\{", "")
                            .replaceAll("}", "")
                            .replaceAll("RequestMethod.", "")
                            .split(",")
            );
        }
        return mappingAnnotation.getShortName();
    }

    /**
     * 获取请求路径
     *
     * @param annotation 目标注解
     * @return url
     */
    private String getRequestUrl(PsiAnnotation annotation) {
        if (Objects.isNull(annotation)) {
            return "";
        }
        PsiAnnotationMemberValue value = annotation.findAttributeValue("value");
        if (Objects.isNull(value)) {
            return "";
        }
        String url = value.getText().replaceAll("\"", "");
        return StringUtils.isBlank(url) ? url : (url.startsWith("/") ? url : "/" + url);
    }

    /**
     * 获取RequestMapping相关注解
     *
     * @param method 目标方法
     * @return 获取到的注解
     */
    private PsiAnnotation getMappingAnnotation(PsiMethod method) {
        return getMappingAnnotation(method.getAnnotations());
    }

    /**
     * 获取RequestMapping相关注解
     *
     * @param javaClass 目标类
     * @return 获取到的注解
     */
    private PsiAnnotation getMappingAnnotation(PsiClass javaClass) {
        return getMappingAnnotation(javaClass.getAnnotations());
    }

    /**
     * 获取RequestMapping相关注解
     *
     * @param annotations 目标注解数组
     * @return 获取到的注解
     */
    private PsiAnnotation getMappingAnnotation(PsiAnnotation[] annotations) {
        for (PsiAnnotation annotation : annotations) {
            SpringWebMappingAnnotation mapping = SpringWebMappingAnnotation.byQualifiedName(annotation.getQualifiedName());
            if (mapping != null) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 获取所有带有spring RequestMapping注解的方法
     *
     * @return 方法数组
     */
    private List<PsiMethod> getMappingMethods() {
        return Stream.of(javaClass.getMethods())
                .filter(m ->
                        Arrays.stream(m.getAnnotations())
                                .map(PsiAnnotation::getQualifiedName)
                                .anyMatch(mappingAnnotationNames::contains)
                )
                .collect(Collectors.toList());
    }
}

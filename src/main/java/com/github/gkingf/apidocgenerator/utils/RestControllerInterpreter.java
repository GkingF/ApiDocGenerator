package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.dto.ApiDto;
import com.github.gkingf.apidocgenerator.spring.SpringWebMappingAnnotation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
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
    private final Project project;
    private final Module module;

    private RestControllerInterpreter(PsiJavaFile javaFile) {
        this.project = javaFile.getProject();
        this.module = ModuleUtil.findModuleForPsiElement(javaFile);
        this.javaFile = javaFile;
        this.fileClass = javaFile.getClasses()[0];
        mappingAnnotationNames = Arrays.stream(SpringWebMappingAnnotation.values())
                .map(SpringWebMappingAnnotation::getQualifiedName)
                .collect(Collectors.toSet());
    }

    public static RestControllerInterpreter getInstance(PsiJavaFile javaFile) {
        return new RestControllerInterpreter(javaFile);
    }

    public List<ApiDto> generateApiDto() {

        String contextPath = Arrays.stream(ArrayUtils.concatAll(
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
                .orElse("/");
        String prefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;

        return getMappingMethods().stream()
                .map(m -> {
                    ApiDto dto = new ApiDto();
                    dto.setComment(PsiJavaUtils.getCommentDescription(m.getDocComment()));
                    dto.setPrefix(prefix);
                    dto.setUrl("url");
                    dto.setMethod(null);
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
     * 获取所有带有spring RequestMapping注解的方法
     *
     * @return 方法数组
     */
    private List<PsiMethod> getMappingMethods() {
        return Stream.of(fileClass.getMethods())
                .filter(m ->
                        Arrays.stream(m.getAnnotations())
                                .map(PsiAnnotation::getQualifiedName)
                                .anyMatch(mappingAnnotationNames::contains)
                )
                .collect(Collectors.toList());
    }
}

package com.github.gkingf.apidocgenerator.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsiJavaFileInterpreter {

    private final PsiJavaFile javaFile;
    private final PsiClass fileClass;

    private PsiJavaFileInterpreter(PsiJavaFile javaFile) {
        this.javaFile = javaFile;
        this.fileClass = javaFile.getClasses()[0];
    }

    public static PsiJavaFileInterpreter getInstance(PsiJavaFile javaFile) {
        return new PsiJavaFileInterpreter(javaFile);
    }

    /**
     * 获取类注释
     *
     * @return 注释文本
     */
    public String getClassComments() {
        return getCommentDescription(javaFile.getClasses()[0].getDocComment());
    }

    /**
     * 获取所有方法
     *
     * @return 方法数组
     */
    public List<PsiMethod> getMethods() {
        return List.of(fileClass.getMethods());
    }

    /**
     * 获取方法描述
     *
     * @param method 目标方法
     */
    public String getMethodDescription(PsiMethod method) {
        return getCommentDescription(method.getDocComment());
    }

    /**
     * comment转文本，去除注释中含有的空白符，并去除首尾空格
     *
     * @param comment 待转换的注释
     * @return 转换结果
     */
    private String getCommentDescription(PsiDocComment comment) {
        return Objects.isNull(comment) ?
                "" : Arrays.stream(comment.getDescriptionElements())
                .filter(e -> !(e instanceof PsiWhiteSpace))
                .map(e -> e.getText().trim())
                .collect(Collectors.joining())
                .trim();
    }
}

package com.github.gkingf.apidocgenerator.utils;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsiJavaFileInterpreter {

    private final PsiJavaFile javaFile;

    private PsiJavaFileInterpreter(PsiJavaFile javaFile) {
        this.javaFile = javaFile;
    }

    public static PsiJavaFileInterpreter getInstance(PsiJavaFile javaFile) {
        return new PsiJavaFileInterpreter(javaFile);
    }

    /**
     * 获取类注释
     */
    public String getClassComments() {
        PsiDocComment docComment = javaFile.getClasses()[0].getDocComment();
        return Objects.isNull(docComment) ?
                "" : Arrays.stream(docComment.getDescriptionElements())
                .filter(e -> !(e instanceof PsiWhiteSpace))
                .map(e -> e.getText().trim())
                .collect(Collectors.joining())
                .trim();
    }
}

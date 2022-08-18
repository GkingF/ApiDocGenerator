package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.spring.SpringWebControllerAnnotation;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsiJavaUtils {

    /**
     * 判断该PsiJavaFile是否包含RestController注解
     */
    public static boolean isController(PsiJavaFile file) {
        PsiClass[] classes = file.getClasses();
        for (PsiClass pClass : classes) {
            for (PsiAnnotation psiAnnotation : pClass.getAnnotations()) {
                if (Objects.equals(psiAnnotation.getQualifiedName(), SpringWebControllerAnnotation.REST_CONTROLLER.getQualifiedName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * comment转文本，去除注释中含有的空白符，并去除首尾空格
     *
     * @param comment 待转换的注释
     * @return 转换结果
     */
    public static String getCommentDescription(PsiDocComment comment) {
        return Objects.isNull(comment) ?
                "" : Arrays.stream(comment.getDescriptionElements())
                .filter(e -> !(e instanceof PsiWhiteSpace))
                .map(e -> e.getText().trim())
                .collect(Collectors.joining())
                .trim();
    }

}

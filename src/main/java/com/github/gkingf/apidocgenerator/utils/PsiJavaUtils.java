package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.spring.SpringWebAnnotation;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

import java.util.Objects;

public class PsiJavaUtils {

    /**
     * 判断该PsiJavaFile是否包含RestController注解
     */
    public static boolean isController(PsiJavaFile file) {
        PsiClass[] classes = file.getClasses();
        for (PsiClass pClass : classes) {
            for (PsiAnnotation psiAnnotation : pClass.getAnnotations()) {
                if (Objects.equals(psiAnnotation.getQualifiedName(), SpringWebAnnotation.REST_CONTROLLER.getQualifiedName())) {
                    return true;
                }
            }
        }
        return false;
    }
}

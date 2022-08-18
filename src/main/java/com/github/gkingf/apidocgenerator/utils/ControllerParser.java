package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.views.ExportDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;

import java.util.List;

public class ControllerParser {

    /**
     * 解析controller
     */
    public static void parse(AnActionEvent e) {
        RestControllerInterpreter interpreter = RestControllerInterpreter.getInstance(PsiUtils.getPsiJavaFile(e));

        String classComments = interpreter.getClassComments();

        List<PsiMethod> methods = interpreter.getMappingMethods();
        PsiMethod method = methods.get(0);
        System.out.println("method.getName() = " + method.getName());
        PsiAnnotation[] annotations = method.getAnnotations();
        System.out.println("annotations[0].getQualifiedName() = " + annotations[0].getQualifiedName());

        ExportDialog.show(classComments);
    }
}

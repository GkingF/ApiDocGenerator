package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.views.ExportDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiMethod;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerParser {

    /**
     * 解析controller
     */
    public static void parse(AnActionEvent e) {
        PsiJavaFileInterpreter interpreter = PsiJavaFileInterpreter.getInstance(PsiUtils.getPsiJavaFile(e));

        String classComments = interpreter.getClassComments();

        List<PsiMethod> methods = interpreter.getMethods();
        String methodComments = methods.stream().map(interpreter::getMethodDescription).collect(Collectors.joining("\n"));

        ExportDialog.show(methodComments);
    }
}

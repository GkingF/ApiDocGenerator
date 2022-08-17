package com.github.gkingf.apidocgenerator.utils;

import com.github.gkingf.apidocgenerator.views.ExportDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ControllerParser {

    /**
     * 解析controller
     */
    public static void parse(AnActionEvent e) {
        PsiJavaFileInterpreter interpreter = PsiJavaFileInterpreter.getInstance(PsiUtils.getPsiJavaFile(e));

        String classComments = interpreter.getClassComments();

        ExportDialog.show(classComments);
    }
}

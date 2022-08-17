package com.github.gkingf.apidocgenerator.handler;

import com.github.gkingf.apidocgenerator.utils.PsiJavaUtils;
import com.github.gkingf.apidocgenerator.utils.PsiUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ControllerHandler implements UpdateHandler {
    @Override
    public boolean optionAvailable(AnActionEvent e) {
        return PsiJavaUtils.isController(PsiUtils.getPsiJavaFile(e));
    }
}

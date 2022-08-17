package com.github.gkingf.apidocgenerator.handler;

import com.github.gkingf.apidocgenerator.utils.PsiUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiJavaFile;

public class JavaFileHandler implements UpdateHandler {
    @Override
    public boolean optionAvailable(AnActionEvent e) {
        return PsiUtils.contextFile(e) instanceof PsiJavaFile;
    }
}

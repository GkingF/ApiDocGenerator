package com.github.gkingf.apidocgenerator.handler;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

public class EditAvailableHandler implements UpdateHandler {
    @Override
    public boolean optionAvailable(AnActionEvent e) {
        return e.getData(PlatformDataKeys.EDITOR) != null;
    }
}

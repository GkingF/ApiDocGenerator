package com.github.gkingf.apidocgenerator.action;

import com.github.gkingf.apidocgenerator.handler.ControllerHandler;
import com.github.gkingf.apidocgenerator.handler.EditAvailableHandler;
import com.github.gkingf.apidocgenerator.handler.JavaFileHandler;
import com.github.gkingf.apidocgenerator.utils.ControllerParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ApiMdDocGenerator extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ControllerParser.parse(e);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        boolean optionAvailable = new JavaFileHandler().optionAvailable(e)
                && new ControllerHandler().optionAvailable(e)
                && new EditAvailableHandler().optionAvailable(e);
        e.getPresentation().setEnabled(optionAvailable);
    }
}
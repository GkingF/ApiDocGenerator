package com.github.gkingf.apidocgenerator.action;

import com.github.gkingf.apidocgenerator.handler.ControllerHandler;
import com.github.gkingf.apidocgenerator.handler.EditAvailableHandler;
import com.github.gkingf.apidocgenerator.handler.JavaFileHandler;
import com.github.gkingf.apidocgenerator.views.DocumentExportDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ApiMdDocGenerator extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String testContent = "{\n" +
                "    \"code\": 0,\n" +
                "    \"data\": \"757216589476003840\",\n" +
                "    \"message\": \"\",\n" +
                "    \"success\": true\n" +
                "}";
        openDialog(testContent);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        boolean optionAvailable = new JavaFileHandler().optionAvailable(e)
                && new ControllerHandler().optionAvailable(e)
                && new EditAvailableHandler().optionAvailable(e);
        e.getPresentation().setEnabled(optionAvailable);
    }

    private void openDialog(String content) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.width;
        double height = screenSize.height;
        DocumentExportDialog dialog = new DocumentExportDialog(content);
        dialog.pack();
        dialog.setSize((int) (width * 0.25), (int) (height * 0.5));
        dialog.setLocation((int) (width * 0.33), (int) (height * 0.2));
        dialog.setVisible(true);
    }
}
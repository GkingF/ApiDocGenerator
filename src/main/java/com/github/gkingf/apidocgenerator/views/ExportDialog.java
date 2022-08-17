package com.github.gkingf.apidocgenerator.views;

import java.awt.*;

public class ExportDialog {
    public static void show(String content) {
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

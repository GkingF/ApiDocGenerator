package com.github.gkingf.apidocgenerator.handler;

import com.intellij.openapi.actionSystem.AnActionEvent;

public interface UpdateHandler {

    /**
     * 判断当前菜单上下文下文档生成选项是否可用
     *
     * @return 是否可用
     */
    boolean optionAvailable(AnActionEvent e);
}

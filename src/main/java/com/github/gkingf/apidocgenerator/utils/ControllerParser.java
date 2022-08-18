package com.github.gkingf.apidocgenerator.utils;

import cn.hutool.json.JSONUtil;
import com.github.gkingf.apidocgenerator.dto.ApiDto;
import com.github.gkingf.apidocgenerator.views.ExportDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.List;

public class ControllerParser {

    /**
     * 解析controller
     */
    public static void parse(AnActionEvent e) {
        RestControllerInterpreter interpreter = RestControllerInterpreter.getInstance(PsiUtils.getPsiJavaFile(e));
        List<ApiDto> apis = interpreter.generateApiDto();
        String s = JSONUtil.toJsonPrettyStr(apis);

        ExportDialog.show(s);
    }
}

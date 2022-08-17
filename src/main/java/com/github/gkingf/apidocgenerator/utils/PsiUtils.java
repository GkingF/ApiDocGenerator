package com.github.gkingf.apidocgenerator.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PsiUtils {

    /**
     * 可获取菜单触发位置的文件类型
     */
    public static @Nullable PsiFile contextFile(AnActionEvent e) {
        return e.getData(LangDataKeys.PSI_FILE);
    }

    /**
     * 获取PsiJavaFile
     */
    public static @NotNull PsiJavaFile getPsiJavaFile(AnActionEvent e) {
        PsiFile psiFile = contextFile(e);
        if (Objects.isNull(psiFile)) {
            throw new NullPointerException("AcActionEvent not include any type of file");
        }
        if (psiFile instanceof PsiJavaFile) {
            return (PsiJavaFile) psiFile;
        }
        throw new IllegalArgumentException("not a PsiJavaFile");
    }
}

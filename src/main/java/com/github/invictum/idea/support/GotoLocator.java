package com.github.invictum.idea.support;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;

public class GotoLocator extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Caret caret = e.getData(LangDataKeys.CARET);
        if (psiFile != null && caret != null) {
            PsiElement element = psiFile.findElementAt(caret.getOffset());
            PsiMethodCallExpression method = PsiTreeUtil.getParentOfType(element, PsiMethodCallExpression.class);
            e.getPresentation().setEnabled(method != null && InvictumMethodsUtil.isCompatible(method));
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Caret caret = e.getData(LangDataKeys.CARET);
        Project project = e.getProject();
        if (psiFile != null && caret != null) {
            PsiElement element = psiFile.findElementAt(caret.getOffset());
            PsiMethodCallExpression method = PsiTreeUtil.getParentOfType(element, PsiMethodCallExpression.class);
            if (method != null && InvictumMethodsUtil.isCompatible(method)) {
                /* Search for literal in related file */
                String file = psiFile.getVirtualFile().getNameWithoutExtension();
                PsiFile[] candidates = PsiShortNamesCache.getInstance(e.getProject()).getFilesByName(file + ".yml");
                if (candidates.length == 1) {
                    PsiFile target = candidates[0];
                    target.navigate(true);
                    /* Try to set directly to line */
                    String pattern = element.getText().substring(1, element.getTextLength() - 1);
                    int offset = target.getText().indexOf(pattern);
                    CaretModel model = FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel();
                    model.moveToOffset(offset);
                }
            }
        }
    }
}

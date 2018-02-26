package com.github.invictum.idea.support;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiTreeUtil;

import static com.github.invictum.idea.support.NavigatorUtil.goToFile;
import static com.github.invictum.idea.support.NavigatorUtil.goToLine;

public class GotoLocatorAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        Caret caret = e.getData(LangDataKeys.CARET);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (caret != null && psiFile != null) {
            PsiElement element = psiFile.findElementAt(caret.getOffset());
            /* Determinate method name and parent class context */
            PsiClass clazz = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            PsiMethodCallExpression method = PsiTreeUtil.getParentOfType(element, PsiMethodCallExpression.class);
            e.getPresentation().setEnabled(method != null && clazz != null && InvictumMethodsUtil
                    .isMethodCompatible(method) && InvictumMethodsUtil.isClassCompatible(clazz));
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Caret caret = e.getData(LangDataKeys.CARET);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (caret != null && psiFile != null) {
            PsiElement element = psiFile.findElementAt(caret.getOffset());
            if (element != null) {
                goToFile(psiFile).ifPresent(target -> goToLine(target, element));
            }
        }
    }
}

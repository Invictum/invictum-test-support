package com.github.invictum.idea.support;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.Optional;

public class NavigatorUtil {

    private final static String EXT = ".yml";

    /**
     * Search for target YML markup file and opens it in editor if possible
     *
     * @param psiFile to search YML for
     * @return navigated PsiFile if found, empty optional otherwise
     */
    public static Optional<PsiFile> goToFile(PsiFile psiFile) {
        String file = psiFile.getVirtualFile().getNameWithoutExtension();
        PsiFile[] candidates = PsiShortNamesCache.getInstance(psiFile.getProject()).getFilesByName(file + EXT);
        if (candidates.length == 1) {
            candidates[0].navigate(true);
            return Optional.of(candidates[0]);
        }
        return Optional.empty();
    }

    /**
     * Set caret in the target {@link PsiFile} if possible, do nothing otherwise
     *
     * @param target  PsiFile to set caret for
     * @param element to use for caret position search
     */
    public static void goToLine(PsiFile target, PsiElement element) {
        String pattern = element.getText().substring(1, element.getTextLength() - 1);
        int offset = target.getText().indexOf(pattern);
        CaretModel caret = FileEditorManager.getInstance(element.getProject()).getSelectedTextEditor().getCaretModel();
        caret.moveToOffset(offset);
    }
}

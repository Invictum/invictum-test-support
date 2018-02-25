package com.github.invictum.idea.support;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import java.util.stream.Stream;

public class InvictumMethodsUtil {

    private static final String[] methods = new String[]{"locate", "locateAll", "data", "locator", "locatorValue"};

    public static boolean isCompatible(PsiMethodCallExpression method) {
        PsiMethod psiMethod = method.resolveMethod();
        return (psiMethod != null) && Stream.of(methods).anyMatch(item -> item.contentEquals(psiMethod.getName()));
    }
}

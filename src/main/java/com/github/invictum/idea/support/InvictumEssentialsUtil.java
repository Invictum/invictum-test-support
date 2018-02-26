package com.github.invictum.idea.support;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import java.util.stream.Stream;

public class InvictumEssentialsUtil {

    /* Define a list of Invictum framework essential methods */
    private static final String[] methods = new String[]{
            "locate",
            "locateAll",
            "data",
            "locator",
            "locatorValue"
    };

    /* Define a list of Invictum framework essential classes */
    private static final String[] classes = new String[]{
            "com.github.invictum.panels.AbstractPanel",
            "com.github.invictum.pages.AbstractPage"
    };

    /**
     * Checks is specified {@link PsiMethodCallExpression} related to Invictum essentials methods
     *
     * @param method to check to
     * @return true if method is related to essentials methods, false otherwise
     */
    public static boolean isMethodCompatible(PsiMethodCallExpression method) {
        PsiMethod psiMethod = method.resolveMethod();
        return (psiMethod != null) && Stream.of(methods).anyMatch(item -> item.contentEquals(psiMethod.getName()));
    }

    /**
     * Checks is specified PsiClass is a child for Invictum essential classes
     *
     * @param candidateClass to check parent for
     * @return true if class is compatible, false otherwise
     */
    public static boolean isClassCompatible(PsiClass candidateClass) {
        if (candidateClass == null) {
            return false;
        }
        String name = candidateClass.getQualifiedName();
        /* No parent or Object class detected */
        if (name == null || name.contentEquals("java.lang.Object")) {
            return false;
        }
        /* Compatible class detection */
        if (Stream.of(classes).anyMatch(candidate -> candidate.contentEquals(name))) {
            return true;
        }
        /* Try with superclass */
        return isClassCompatible(candidateClass.getSuperClass());
    }
}

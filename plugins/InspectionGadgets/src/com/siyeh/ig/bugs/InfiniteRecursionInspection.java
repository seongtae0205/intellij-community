package com.siyeh.ig.bugs;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import com.siyeh.ig.GroupNames;
import com.siyeh.ig.MethodInspection;
import com.siyeh.ig.psiutils.RecursionUtils;

public class InfiniteRecursionInspection extends MethodInspection {

    public String getDisplayName() {
        return "Infinite recursion";
    }

    public String getGroupDisplayName() {
        return GroupNames.BUGS_GROUP_NAME;
    }

    public boolean isEnabledByDefault(){
        return true;
    }
    public String buildErrorString(PsiElement location) {
        return "Method #ref recurses infinitely, and can only end by throw an exception #loc";
    }

    public BaseInspectionVisitor createVisitor(InspectionManager inspectionManager, boolean onTheFly) {
        return new InfiniteRecursionVisitor(this, inspectionManager, onTheFly);
    }

    private static class InfiniteRecursionVisitor extends BaseInspectionVisitor {
        private InfiniteRecursionVisitor(BaseInspection inspection, InspectionManager inspectionManager, boolean isOnTheFly) {
            super(inspection, inspectionManager, isOnTheFly);
        }

        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
            if (method.hasModifierProperty(PsiModifier.ABSTRACT)) {
                return;
            }
            if (!RecursionUtils.methodMayRecurse(method)) {
                return;
            }
            if (!RecursionUtils.methodMustRecurseBeforeReturning(method)) {
                return;
            }
            registerMethodError(method);
        }

    }

}

/*
 * Copyright 2011 Stephen Connolly
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.github.stephenc.intellij.plugin.generateserializationhelpers;

import com.intellij.codeInsight.generation.ClassMember;
import com.intellij.codeInsight.generation.GenerateMembersHandlerBase;
import com.intellij.codeInsight.generation.GenerationInfo;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.codeInsight.generation.PsiElementClassMember;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.MethodSignatureUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class GenerateMethodHandler extends GenerateMembersHandlerBase {
    /**cannot return empty array, but this result won't be used anyway */
    private static final PsiElementClassMember[] DUMMY_RESULT = new PsiElementClassMember[1];

    public GenerateMethodHandler() {
        super("");
    }

    @Override
    protected ClassMember[] chooseOriginalMembers(PsiClass aClass, Project project, Editor editor) {
        final PsiClassType javaLangObject =
                PsiType.getJavaLangObject(PsiManager.getInstance(project), aClass.getResolveScope());
        PsiMethod readResolve = findMethod(aClass, getMethodSignature(javaLangObject));
        if (readResolve != null) {
            return null;
        }
        return DUMMY_RESULT;
    }

    @Override
    protected ClassMember[] getAllOriginalMembers(PsiClass psiClass) {
        return null;
    }

    @NotNull
    @Override
    protected List<? extends GenerationInfo> generateMemberPrototypes(PsiClass aClass, ClassMember[] classMembers)
            throws IncorrectOperationException {
        Project project = aClass.getProject();
        final PsiClassType javaLangObject =
                PsiType.getJavaLangObject(PsiManager.getInstance(project), aClass.getResolveScope());

        PsiMethod readResolve = findMethod(aClass, getMethodSignature(javaLangObject));
        if (readResolve != null) {
            return Collections.emptyList();
        }
        return OverrideImplementUtil.convert2GenerationInfos(Collections.singleton(
                createMethod(project,
                        JavaPsiFacade.getInstance(project).getElementFactory(),
                        CodeStyleManager.getInstance(project))));
    }

    @Override
    protected GenerationInfo[] generateMemberPrototypes(PsiClass aClass, ClassMember classMember)
            throws IncorrectOperationException {
        return null;
    }

    protected static PsiMethod findMethod(PsiClass aClass, MethodSignature signature) {
        return MethodSignatureUtil.findMethodBySignature(aClass, signature, false);
    }

    protected abstract MethodSignature getMethodSignature(PsiClassType javaLangObject);
    protected abstract PsiMethod createMethod(Project myProject, PsiElementFactory myFactory, CodeStyleManager myCodeStyleManager)
            throws IncorrectOperationException;


}

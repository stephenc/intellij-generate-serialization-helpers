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

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.MethodSignatureUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;

public class GenerateWriteReplaceHandler extends GenerateMethodHandler {

    protected MethodSignature getMethodSignature(PsiClassType javaLangObject) {
        return MethodSignatureUtil
                .createMethodSignature("writeReplace", new PsiType[]{javaLangObject}, PsiTypeParameter.EMPTY_ARRAY,
                        PsiSubstitutor.EMPTY);
    }

    protected PsiMethod createMethod(Project myProject, PsiElementFactory myFactory,
                                     CodeStyleManager myCodeStyleManager)
            throws IncorrectOperationException {
        @NonNls StringBuilder buffer = new StringBuilder();

        buffer.append("/**\n");
        buffer.append(" * Called when object is to be serialized on a stream to allow the object to substitute a proxy for itself. \n");
        buffer.append(" *\n");
        buffer.append(" * @return {@code this}, or the proxy for {@code this}.\n");
        buffer.append(" * @throws java.io.ObjectStreamException if the object cannot be proxied.\n");
        buffer.append(
                " * @see <a href=\"http://download.oracle.com/javase/1.3/docs/guide/serialization/spec/output.doc5"
                        + ".html\">The Java Object Serialization Specification</a>\n");
        buffer.append(" */\n");
        buffer.append("private Object writeReplace() throws java.io.ObjectStreamException {\nreturn this;}");
        PsiMethod hashCode = myFactory.createMethodFromText(buffer.toString(), null);
        return (PsiMethod) myCodeStyleManager.reformat(hashCode);
    }
}

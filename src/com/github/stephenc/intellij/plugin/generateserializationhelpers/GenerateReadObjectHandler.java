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

public class GenerateReadObjectHandler extends GenerateMethodHandler {

    protected MethodSignature getMethodSignature(PsiClassType javaLangObject) {
        return MethodSignatureUtil
                .createMethodSignature("readObject", PsiType.EMPTY_ARRAY, PsiTypeParameter.EMPTY_ARRAY,
                        PsiSubstitutor.EMPTY);
    }

    protected PsiMethod createMethod(Project myProject, PsiElementFactory myFactory, CodeStyleManager myCodeStyleManager)
            throws IncorrectOperationException {
        @NonNls StringBuilder buffer = new StringBuilder();

        buffer.append("/**\n");
        buffer.append(" * Called when object is to be deserialized from a stream.\n");
        buffer.append(" *\n");
        buffer.append(" * @param stream the stream to read the object from.\n");
        buffer.append(" * @throws java.io.IOException    if the object could not be read.\n");
        buffer.append(" * @throws java.lang.ClassNotFoundException if a class required to read the object could not be found.\n");
        buffer.append(" * @see <a href=\"http://download.oracle.com/javase/1.3/docs/guide/serialization/spec/input"
                + ".doc4.html\">The Java\n");
        buffer.append(" *      Object Serialization Specification</a>\n");
        buffer.append(" */\n");
        buffer.append("private void readObject(java.io.ObjectInputStream stream) throws java.lang.ClassNotFoundException, java.io.IOException { stream.defaultReadObject();}");
        PsiMethod hashCode = myFactory.createMethodFromText(buffer.toString(), null);
        return (PsiMethod) myCodeStyleManager.reformat(hashCode);
    }
}

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

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiAnonymousClass;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class GenerateMethodAction extends BaseGenerateAction {
    public GenerateMethodAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    protected PsiClass getTargetClass(Editor editor, PsiFile file) {
      final PsiClass targetClass = super.getTargetClass(editor, file);
      if (targetClass == null || targetClass instanceof PsiAnonymousClass ||
          targetClass.isEnum()) return null;
      return targetClass;
    }
}

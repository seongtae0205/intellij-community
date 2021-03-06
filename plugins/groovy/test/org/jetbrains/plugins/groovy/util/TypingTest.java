// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.groovy.util;

import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrExpression;

import static com.intellij.testFramework.UsefulTestCase.assertInstanceOf;
import static com.intellij.util.ArrayUtil.getLastElement;
import static org.jetbrains.plugins.groovy.LightGroovyTestCase.assertType;

public interface TypingTest {

  @NotNull
  CodeInsightTestFixture getFixture();

  default void typingTest(@Language("Groovy") String text, @Nullable String expectedType) {
    final GroovyFile file = (GroovyFile)getFixture().configureByText("_.groovy", text);
    final GrStatement lastStatement = getLastElement(file.getStatements());
    assertInstanceOf(lastStatement, GrExpression.class);
    assertType(expectedType, ((GrExpression)lastStatement).getType());
  }
}

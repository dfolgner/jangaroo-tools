/*
 * Copyright 2008 CoreMedia AG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language 
 * governing permissions and limitations under the License.
 */

package net.jangaroo.jooc.ast;

import net.jangaroo.jooc.JooSymbol;
import net.jangaroo.jooc.Jooc;
import net.jangaroo.jooc.Scope;
import net.jangaroo.jooc.sym;

import java.io.IOException;
import java.util.List;

/**
 * @author Andreas Gawecki
 * @author Frank Wienberg
 */
public class Parameter extends IdeDeclaration {

  private JooSymbol optSymConstOrRest;
  private TypeRelation optTypeRelation;
  private Initializer optInitializer;

  public Parameter(JooSymbol optSymConst, Ide ide, TypeRelation optTypeRelation, Initializer optInitializer) {
    super(ide);
    this.optSymConstOrRest = optSymConst;
    this.optTypeRelation = optTypeRelation;
    this.optInitializer = optInitializer;
  }

  @Override
  public List<? extends AstNode> getChildren() {
    return makeChildren(super.getChildren(), optTypeRelation, optInitializer);
  }

  @Override
  public void visit(AstVisitor visitor) throws IOException {
    visitor.visitParameter(this);
  }

  @Override
  public void scope(final Scope scope) {
    super.scope(scope);
    if (getOptTypeRelation() != null) {
      getOptTypeRelation().scope(scope);
    }
    if (getOptInitializer() != null) {
      getOptInitializer().scope(scope);
    }
  }

  @Override
  public void analyze(AstNode parentNode) {
    super.analyze(parentNode);
    if (getOptTypeRelation() != null) {
      getOptTypeRelation().analyze(this);
      if (isRest() && !"Array".equals(getOptTypeRelation().getType().getSymbol().getText())) {
        //todo replace that condition with real Array definition lookup
        throw Jooc.error(getOptTypeRelation().getSymbol(), "Rest parameter must have Array type.");
      }
    }
    if (getOptInitializer() != null) {
      getOptInitializer().analyze(this);
      if (!getOptInitializer().getValue().isCompileTimeConstant()) {
        throw Jooc.error(getOptInitializer().getSymbol(), "Parameter initializer must be compile-time constant.");
      }
    }
  }

  public boolean isRest() {
    return getOptSymConstOrRest() != null && getOptSymConstOrRest().sym == sym.REST;
  }

  public boolean hasInitializer() {
    return getOptInitializer() != null &&
            // ignore initializers that assign undefined. Parameters are already undefined if not present.
            (!(getOptInitializer().getValue() instanceof IdeExpr) ||
                    !((IdeExpr) getOptInitializer().getValue()).getIde().getName().equals("undefined"));
  }

  @Override
  public IdeDeclaration resolveDeclaration() {
    return getOptTypeRelation() == null ? null : getOptTypeRelation().getType().resolveDeclaration();
  }


  public JooSymbol getOptSymConstOrRest() {
    return optSymConstOrRest;
  }

  public TypeRelation getOptTypeRelation() {
    return optTypeRelation;
  }

  public Initializer getOptInitializer() {
    return optInitializer;
  }

}

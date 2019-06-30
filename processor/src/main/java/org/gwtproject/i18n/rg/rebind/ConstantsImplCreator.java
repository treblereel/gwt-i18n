/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtproject.i18n.rg.rebind;

import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.rg.util.SourceWriter;
import org.gwtproject.i18n.shared.GwtLocale;

import static org.gwtproject.i18n.rg.rebind.AbstractResource.*;

/**
 * Creates the class implementation for a given resource bundle using the
 * standard <code>AbstractGeneratorClassCreator</code>.
 */
class ConstantsImplCreator extends AbstractLocalizableImplCreator {
  /**
   * Does a Map need to be generated in order to store complex results?
   */
  private boolean needCache = false;

  /**
   * Constructor for <code>ConstantsImplCreator</code>.
   * 
   * @param logger logger to print errors
   * @param writer <code>Writer</code> to print to
   * @param localizableClass class/interface to conform to
   * @param resourceList resource bundle used to generate the class
   * @throws UnableToCompleteException
   */
  public ConstantsImplCreator(TreeLogger logger, SourceWriter writer,
                              TypeElement localizableClass, ResourceList resourceList)
      throws UnableToCompleteException {
    super(logger, writer, localizableClass, resourceList, true);

    throw new UnsupportedOperationException();
/*    try {
      TypeElement stringClass = oracle.getType(String.class.getName());
      TypeElement mapClass = oracle.getType(Map.class.getName());
      TypeElement stringArrayClass = oracle.getArrayType(stringClass);
      TypeElement intClass = oracle.parse(int.class.getName());
      TypeElement doubleClass = oracle.parse(double.class.getName());
      TypeElement floatClass = oracle.parse(float.class.getName());
      TypeElement booleanClass = oracle.parse(boolean.class.getName());
      register(stringClass, new SimpleValueMethodCreator(this,
          SimpleValueMethodCreator.STRING));
      register(mapClass, new ConstantsMapMethodCreator(this));
      register(intClass, new SimpleValueMethodCreator(this,
          SimpleValueMethodCreator.INT));
      register(doubleClass, new SimpleValueMethodCreator(this,
          SimpleValueMethodCreator.DOUBLE));
      register(floatClass, new SimpleValueMethodCreator(this,
          SimpleValueMethodCreator.FLOAT));
      register(booleanClass, new SimpleValueMethodCreator(this,
          SimpleValueMethodCreator.BOOLEAN));

      register(stringArrayClass, new ConstantsStringArrayMethodCreator(this));
    } catch (NotFoundException e) {
      throw error(logger, e);
    } catch (TypeOracleException e) {
      throw error(logger, e);
    }*/
  }

  /**
   * Checks that the method has the right structure to implement
   * <code>Constant</code>.
   * 
   * @param method method to check
   */
  protected void checkConstantMethod(TreeLogger logger, ExecutableElement method)
      throws UnableToCompleteException {
    if (method.getParameters().size() > 0) {
      throw error(logger,
          "Methods in interfaces extending Constant must have no parameters");
    }
    checkReturnType(logger, method);
  }

  /**
   * @param logger
   * @param method
   * @throws UnableToCompleteException
   */
  protected void checkReturnType(TreeLogger logger, ExecutableElement method)
      throws UnableToCompleteException {
/*    TypeElement returnType = method.getReturnType();
    JPrimitiveType primitive = returnType.isPrimitive();
    if (primitive != null
        && (primitive == JPrimitiveType.BOOLEAN
            || primitive == JPrimitiveType.DOUBLE
            || primitive == JPrimitiveType.FLOAT || primitive == JPrimitiveType.INT)) {
      return;
    }
    JArrayType arrayType = returnType.isArray();
    if (arrayType != null) {
      String arrayComponent = arrayType.getComponentType().getQualifiedSourceName();
      if (!arrayComponent.equals("java.lang.String")) {
        throw error(logger,
            "Methods in interfaces extending Constant only support arrays of Strings");
      }
      return;
    }
    String returnTypeName = returnType.getQualifiedSourceName();
    if (returnTypeName.equals("java.lang.String")) {
      return;
    }
    if (returnTypeName.equals("java.util.Map")) {
      JParameterizedType paramType = returnType.isParameterized();
      if (paramType != null) {
        TypeElement[] typeArgs = paramType.getTypeArgs();
        if (typeArgs.length != 2
            || !typeArgs[0].getQualifiedSourceName().equals("java.lang.String")
            || !typeArgs[1].getQualifiedSourceName().equals("java.lang.String")) {
          throw error(logger,
              "Map Methods in interfaces extending Constant must be raw or <String, String>");
        }
      }
      return;
    }*/
    throw error(logger,
        "Methods in interfaces extending Constant must have a return type of "
            + "String/int/float/boolean/double/String[]/Map");
  }

  @Override
  protected void classEpilog() {
    if (isNeedCache()) {
      getWriter().println("java.util.Map cache = new java.util.HashMap();");
    }
  }

  /**
   * Create the method body associated with the given method. Arguments are
   * arg0...argN.
   */
  @Override
  protected void emitMethodBody(TreeLogger logger, ExecutableElement method,
      GwtLocale locale) throws UnableToCompleteException {
    checkConstantMethod(logger, method);
    delegateToCreator(logger, method, locale);
  }

  boolean isNeedCache() {
    return needCache;
  }

  void setNeedCache(boolean needCache) {
    this.needCache = needCache;
  }
}

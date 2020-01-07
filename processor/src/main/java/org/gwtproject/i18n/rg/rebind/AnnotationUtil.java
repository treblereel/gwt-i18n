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

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;

/**
 * Utility class for i18n-related annotation manipulation routines.
 */
public class AnnotationUtil {

  /**
   * Find an instance of the specified annotation, walking up the inheritance
   * tree if necessary.
   *
   * <p>Note that i18n annotations may appear on classes as well as interfaces
   * (a concrete implementation can be supplied rather than just an interface
   * and this is the normal way of using generic Localizable interfaces), so
   * we have to search the super chain as well as other interfaces.
   *
   * <p>The super chain is walked first, so if an ancestor superclass has the
   * requested annotation, it will be preferred over a directly implemented
   * interface.
   *
   * @param <T> Annotation type to search for
   * @param clazz root class to search, may be null
   * @param annotationClass class object of Annotation subclass to search for
   * @return the requested annotation or null if none
   */
  static <T extends Annotation> T getClassAnnotation(Types types,
                                                     TypeElement clazz,
                                                     Class<T> annotationClass) {
    if (clazz == null) {
      return null;
    }
    T annot = clazz.getAnnotation(annotationClass);
    if (annot == null) {
      List<TypeMirror> list = types.directSupertypes(clazz.asType())
              .stream()
              .collect(Collectors.toList());

      for (int i = list.size() - 1; i >= 0; i--) {
        if(!list.get(i).toString().equals(Object.class.getCanonicalName())) {
          annot = getClassAnnotation(types, MoreTypes.asTypeElement(list.get(i)), annotationClass);
          if (annot != null) {
            return annot;
          }
        }
      }
    }
    return annot;
  }

}

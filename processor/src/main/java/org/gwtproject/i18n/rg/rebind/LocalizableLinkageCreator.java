/*
 * Copyright 2007 Google Inc.
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

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.shared.GwtLocale;

/**
 * Links classes with their localized counterparts.
 */
class LocalizableLinkageCreator extends AbstractSourceCreator {
  
  static Map<String, TypeElement> findDerivedClasses(TreeLogger logger,
                                                     TypeElement baseClass) throws UnableToCompleteException {
    // Construct valid set of candidates for this type.
    Map<String, TypeElement> matchingClasses = new HashMap<String, TypeElement>();
    // Add base class if possible.
    if (!baseClass.getKind().isInterface() && !baseClass.getModifiers().contains(Modifier.ABSTRACT)) {
      matchingClasses.put(GwtLocale.DEFAULT_LOCALE, baseClass);
    }
    String baseName = baseClass.getSimpleName().toString();

    System.out.println("LocalizableLinkageCreator " + baseName + " " + GwtLocale.DEFAULT_LOCALE);

    //throw new UnsupportedOperationException(baseName);

    // Find matching sub types.
 /*   TypeElement[] x = baseClass.getSubtypes();
    for (int i = 0; i < x.length; i++) {
      TypeElement subType = x[i];
      if (!subType.getKind().isInterface() && subType.getModifiers().contains(Modifier.ABSTRACT)) {
        String name = subType.getSimpleName().toString();
        // Strip locale from type,
        int localeIndex = name.indexOf(ResourceFactory.LOCALE_SEPARATOR);
        String subTypeBaseName = name;
        if (localeIndex != -1) {
          subTypeBaseName = name.substring(0, localeIndex);
        }
        boolean matches = subTypeBaseName.equals(baseName);
        if (matches) {
          boolean isDefault = localeIndex == -1
              || localeIndex == name.length() - 1
              || GwtLocale.DEFAULT_LOCALE.equals(name.substring(localeIndex + 1));
          if (isDefault) {
            // Don't override base as default if present.
            TypeElement defaultClass = 
              matchingClasses.get(GwtLocale.DEFAULT_LOCALE);
            if (defaultClass != null) {
              throw error(logger, defaultClass + " and " + baseName
                  + " are both potential default classes for " + baseClass);
            } else {
              matchingClasses.put(GwtLocale.DEFAULT_LOCALE, subType);
            }
          } else {
            // Don't allow a locale to be ambiguous. Very similar to default
            // case, different error message.
            String localeSubString = name.substring(localeIndex + 1);
            TypeElement dopClass = matchingClasses.get(localeSubString);
            if (dopClass != null) {
              throw error(logger, dopClass.getQualifiedName().toString() + " and "
                  + subType.getQualifiedName()
                  + " are both potential matches to " + baseClass
                  + " in locale " + localeSubString);
            }
            matchingClasses.put(localeSubString, subType);
          }
        }
      }
    }*/
    return matchingClasses;
  }

  /**
   * Map to cache linkages of implementation classes and interfaces.
   */
  // Change back to ReferenceMap once apache collections is in.
  private final Map<String, String> implCache = new HashMap<String, String>();

  /**
   * * Finds associated implementation in the current locale. Here are the rules
   * <p>
   * </p>
   * <p>
   * If class name is X, and locale is z_y, look for X_z_y, then X_z, then X
   * </p>
   * 
   * @param baseClass
   * @return class name to link with
   * @throws UnableToCompleteException
   */
  String linkWithImplClass(TreeLogger logger, TypeElement baseClass,
      GwtLocale locale) throws UnableToCompleteException {
    String baseName = baseClass.getQualifiedName().toString();
    /**
     * Try to find implementation class, as the current class is not a Constant
     * or Message.
     */
    String className = implCache.get(baseName + locale.toString());
    if (className != null) {
      return className;
    }

    if (baseClass.getSimpleName().toString().indexOf(ResourceFactory.LOCALE_SEPARATOR) != -1) {
      throw error(logger, "Cannot have a " + ResourceFactory.LOCALE_SEPARATOR
          + " in the base localizable class " + baseClass);
    }
    Map<String, TypeElement> matchingClasses =
      findDerivedClasses(logger, baseClass);
    // Now that we have all matches, find best class
    TypeElement result = null;  
    for (GwtLocale search : locale.getCompleteSearchList()) {
      result = matchingClasses.get(search.toString());
      if (result != null) {
        className = result.getQualifiedName().toString();
        implCache.put(baseName + locale.toString(), className);
        return className;
      }
    }
    // No classes matched.
    throw error(logger, "Cannot find a class to bind to argument type "
            + baseClass.getQualifiedName());
  }
}

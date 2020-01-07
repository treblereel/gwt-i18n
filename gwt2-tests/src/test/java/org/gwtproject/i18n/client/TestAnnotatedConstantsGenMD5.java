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

package org.gwtproject.i18n.client;

import org.gwtproject.i18n.client.LocalizableResource.DefaultLocale;
import org.gwtproject.i18n.client.LocalizableResource.Generate;
import org.gwtproject.i18n.client.LocalizableResource.GenerateKeys;

import java.util.Map;

/**
 * Test of Constants generation using annotations.
 */
//@I18N
@DefaultLocale("en-US")
@GenerateKeys // ("org.gwtproject.i18n.rg.rebind.MD5") - default
// @Generate(format = "org.gwtproject.i18n.rg.rebind.format.PropertiesFormat")
@Generate(format = "org.gwtproject.i18n.server.PropertyCatalogFactory")
public interface TestAnnotatedConstantsGenMD5 extends Constants {

  @DefaultIntValue(14)
  int fourteen();
  
  @DefaultBooleanValue(false)
  boolean isFalse();
  
  @DefaultBooleanValue(true)
  boolean isTrue();
  
  @DefaultStringArrayValue("String array with one string")
  String[] singleString();
  
  @DefaultStringMapValue({"key1", "value1", "key2", "value2"})
  Map<String, String> stringMap();

  @DefaultStringValue("Test me")
  String testMe();
  
  @DefaultFloatValue(13.7f)
  float thirteenPointSeven();
  
  @DefaultDoubleValue(3.14)
  double threePointOneFour();
  
  @DefaultStringArrayValue({"One", "Two"})
  String[] twoStrings();
  
  @DefaultStringValue("Once more, with meaning")
  @Meaning("Mangled quote")
  String withMeaning();
}

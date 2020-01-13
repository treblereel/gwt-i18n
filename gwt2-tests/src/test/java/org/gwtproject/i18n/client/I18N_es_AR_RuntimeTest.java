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
package org.gwtproject.i18n.client;

import junit.framework.TestCase;
import org.gwtproject.i18n.client.I18N_es_MX_Test.MyConstants;
import org.gwtproject.i18n.client.I18N_es_MX_Test.MyMessages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests regional inheritance for es_AR.
 */
public class I18N_es_AR_RuntimeTest extends TestCase {

  {
    setLocale();
  }

  private static void setLocale() {
    System.setProperty("locale", "es_AR");
  }

  public void testAvailableLocales() {
    String[] locales = LocaleInfo.getAvailableLocaleNames();
    Set<String> localeSet = new HashSet<>();
    List<String> localeList = Arrays.asList(locales);
    localeSet.addAll(localeList);
    List<String> expectedList = Arrays.asList("default", "es_419", "es_AR",
        "es_MX");
    assertEquals(expectedList.size(), localeSet.size());
    localeSet.removeAll(expectedList);
    assertEquals(0, localeSet.size());
  }

  public void testCurrencyNames() {
    assertEquals("peso argentino", CurrencyList.get().lookupName("ARS"));
    assertEquals("peso mexicano", CurrencyList.get().lookupName("MXN"));
    assertEquals("dólar estadounidense", CurrencyList.get().lookupName("USD"));
  }

  public void testDefaultCurrency() {
    CurrencyData data = CurrencyList.get().getDefault();
    assertEquals("ARS", data.getCurrencyCode());
    assertEquals("$", data.getCurrencySymbol());
    assertEquals(2, data.getDefaultFractionDigits());
  }
  
  public void testOtherCurrency() {
    CurrencyData ars = CurrencyList.get().lookup("ARS");
    assertEquals("ARS", ars.getCurrencyCode());
    assertEquals("$", ars.getCurrencySymbol());
    assertEquals(2, ars.getDefaultFractionDigits());
    CurrencyData data = CurrencyList.get().lookup("MXN");
    assertEquals("MXN", data.getCurrencyCode());
    assertEquals("MX$", data.getCurrencySymbol());
    assertEquals(2, data.getDefaultFractionDigits());
    CurrencyData usd = CurrencyList.get().lookup("USD");
    assertEquals("USD", usd.getCurrencyCode());
    assertEquals("US$", usd.getCurrencySymbol());
    assertEquals(2, usd.getDefaultFractionDigits());
    boolean found = false;
    for (CurrencyData it : CurrencyList.get()) {
      if ("USD".equals(it.getCurrencyCode())) {
        assertEquals("US$", it.getCurrencySymbol());
        assertEquals(2, it.getDefaultFractionDigits());
        found = true;
        break;
      }
    }
    assertTrue("Did not find USD in iterator", found);
  }

  public void testRegionalInheritance() {
    MyMessages msg = new I18N_es_MX_TestMyMessages_();
    assertEquals("es_419", msg.getSourceLocale());
    MyConstants cst = new I18N_es_MX_TestMyConstants_();
    // Since our copile-time locale is es_419 (Latin America), we do
    // not get es_019 (Central America) in the inheritance chain for
    // es_AR as only the compile-time locales are used for translation
    // inheritance.
    assertEquals("default", cst.getSourceLocale());
  }
  
  public void testRuntimeLocale() {
    assertEquals("es_AR", LocaleInfo.getCurrentLocale().getLocaleName());
  }
}

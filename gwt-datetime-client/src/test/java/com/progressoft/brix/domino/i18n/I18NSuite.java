/*
 * Copyright 2013 Google Inc.
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
package com.progressoft.brix.domino.i18n;

import com.progressoft.brix.domino.i18n.client.AnnotationsTest;
import com.progressoft.brix.domino.i18n.client.ArabicPluralsTest;
import com.progressoft.brix.domino.i18n.client.CurrencyTest;
import com.progressoft.brix.domino.i18n.client.CustomPluralsTest;
import com.progressoft.brix.domino.i18n.client.DateTimeFormat_de_Test;
import com.progressoft.brix.domino.i18n.client.DateTimeFormat_en_Test;
import com.progressoft.brix.domino.i18n.client.DateTimeFormat_fil_Test;
import com.progressoft.brix.domino.i18n.client.DateTimeFormat_pl_Test;
import com.progressoft.brix.domino.i18n.client.DateTimeParse_en_Test;
import com.progressoft.brix.domino.i18n.client.DateTimeParse_zh_CN_Test;
import com.progressoft.brix.domino.i18n.client.I18N2Test;
import com.progressoft.brix.domino.i18n.client.I18NTest;
import com.progressoft.brix.domino.i18n.client.I18N_en_US_Test;
import com.progressoft.brix.domino.i18n.client.I18N_es_AR_RuntimeTest;
import com.progressoft.brix.domino.i18n.client.I18N_es_MX_RuntimeTest;
import com.progressoft.brix.domino.i18n.client.I18N_es_MX_Test;
import com.progressoft.brix.domino.i18n.client.I18N_iw_Test;
import com.progressoft.brix.domino.i18n.client.I18N_nb_Test;
import com.progressoft.brix.domino.i18n.client.I18N_pa_Arab_Test;
import com.progressoft.brix.domino.i18n.client.I18N_pa_PK_Test;
import com.progressoft.brix.domino.i18n.client.LocaleInfoTest;
import com.progressoft.brix.domino.i18n.client.LocaleInfo_ar_Test;
import com.progressoft.brix.domino.i18n.client.LocalizedNames_default_Test;
import com.progressoft.brix.domino.i18n.client.LocalizedNames_en_Test;
import com.progressoft.brix.domino.i18n.client.NumberFormat_ar_Test;
import com.progressoft.brix.domino.i18n.client.NumberFormat_en_Test;
import com.progressoft.brix.domino.i18n.client.NumberFormat_fr_Test;
import com.progressoft.brix.domino.i18n.client.NumberParse_en_Test;
import com.progressoft.brix.domino.i18n.client.NumberParse_fr_Test;
import com.progressoft.brix.domino.i18n.client.PolishPluralsTest;
import com.progressoft.brix.domino.i18n.client.RuntimeLocalesTest;
import com.progressoft.brix.domino.i18n.client.RussianPluralsTest;
import com.progressoft.brix.domino.i18n.client.TimeZoneInfoTest;
import com.progressoft.brix.domino.i18n.client.TimeZoneTest;
import com.progressoft.brix.domino.i18n.shared.GwtBidiUtilsTest;
import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;

/**
 * I18N tests for client code running as a GWT test.
 */
public class I18NSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("I18N client tests");

    suite.addTestSuite(ArabicPluralsTest.class);
    suite.addTestSuite(AnnotationsTest.class);
    suite.addTestSuite(CurrencyTest.class);
    suite.addTestSuite(CustomPluralsTest.class);
    suite.addTestSuite(DateTimeFormat_de_Test.class);
    suite.addTestSuite(DateTimeFormat_en_Test.class);
    suite.addTestSuite(DateTimeFormat_fil_Test.class);
    suite.addTestSuite(DateTimeFormat_pl_Test.class);
    suite.addTestSuite(DateTimeParse_en_Test.class);
    suite.addTestSuite(DateTimeParse_zh_CN_Test.class);
    suite.addTestSuite(GwtBidiUtilsTest.class);
    suite.addTestSuite(I18NTest.class);
    suite.addTestSuite(I18N2Test.class);
    suite.addTestSuite(I18N_pa_Arab_Test.class);
    suite.addTestSuite(I18N_pa_PK_Test.class);
    suite.addTestSuite(I18N_iw_Test.class);
    suite.addTestSuite(I18N_es_MX_Test.class);
    suite.addTestSuite(I18N_es_MX_RuntimeTest.class);
    suite.addTestSuite(I18N_es_AR_RuntimeTest.class);
    suite.addTestSuite(I18N_en_US_Test.class);
    suite.addTestSuite(I18N_nb_Test.class);
    suite.addTestSuite(LocaleInfo_ar_Test.class);
    suite.addTestSuite(LocaleInfoTest.class);
    suite.addTestSuite(LocalizedNames_default_Test.class);
    suite.addTestSuite(LocalizedNames_en_Test.class);
    suite.addTestSuite(NumberFormat_ar_Test.class);
    suite.addTestSuite(NumberFormat_en_Test.class);
    suite.addTestSuite(NumberFormat_fr_Test.class);
    suite.addTestSuite(NumberParse_en_Test.class);
    suite.addTestSuite(NumberParse_fr_Test.class);
    suite.addTestSuite(PolishPluralsTest.class);
    suite.addTestSuite(RussianPluralsTest.class);
    suite.addTestSuite(RuntimeLocalesTest.class);
    suite.addTestSuite(TimeZoneInfoTest.class);
    suite.addTestSuite(TimeZoneTest.class);

    return suite;
  }
}

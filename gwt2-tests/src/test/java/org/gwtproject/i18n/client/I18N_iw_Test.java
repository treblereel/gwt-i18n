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

import com.google.gwt.core.client.GWT;
import org.gwtproject.i18n.client.impl.CldrImpl;
import com.google.gwt.junit.client.GWTTestCase;

import static org.gwtproject.i18n.client.Constants.*;
import static org.gwtproject.i18n.client.Messages.*;

/**
 * Tests Hebrew deprecated alias.
 */
public class I18N_iw_Test extends GWTTestCase {

  /**
   * Test deprecated locale aliases with Constants.
   */
  @I18N
  public interface MyConstants extends Constants {
    @DefaultStringValue("default")
    String getSourceLocale();
  }
  
  /**
   * Test deprecated locale aliases with Messages.
   */
  @I18N
  public interface MyMessages extends Messages {
    @DefaultMessage("default")
    String getSourceLocale();
  }
  
  @Override
  public String getModuleName() {
    return "org.gwtproject.i18n.I18NTest_iw";
  }

  public void testCldrImpl() {
    CldrImpl cldr = GWT.create(CldrImpl.class);
    assertTrue(cldr.isRTL());
  }

  public void testIwAlias() {
    MyMessages msg = new I18N_iw_TestMyMessages_();
    assertEquals("he", msg.getSourceLocale());
    MyConstants cst = new I18N_iw_TestMyConstants_();
    assertEquals("he", cst.getSourceLocale());
  }
}

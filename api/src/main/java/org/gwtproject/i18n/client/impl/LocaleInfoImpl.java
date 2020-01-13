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
package org.gwtproject.i18n.client.impl;

import org.gwtproject.dom.client.Document;
import org.gwtproject.i18n.client.DateTimeFormatInfo;
import org.gwtproject.i18n.client.LocalizedNames;
import org.gwtproject.i18n.client.constants.NumberConstants;
import org.gwtproject.i18n.client.constants.NumberConstantsImpl;
import org.gwtproject.i18n.client.impl.cldr.DateTimeFormatInfoImpl;
import org.gwtproject.i18n.client.impl.cldr.LocalizedNamesImpl;

/**
 * Implementation detail of LocaleInfo -- not a public API and subject to
 * change.
 *
 * Generated interface for locale information.  The default implementation
 * returns null, which is used if the i18n module is not imported.
 *
 * @see org.gwtproject.i18n.client.LocaleInfo
 */
public class LocaleInfoImpl {

  /**
   * Returns the runtime locale (note that this requires the i18n locale property
   * provider's assistance).
   */
  static String getRuntimeLocale() {
    return System.getProperty("locale");
  }

  /**
   * Returns an array of available locale names.
   */
  public String[] getAvailableLocaleNames() {
    return org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get().getAvailableLocaleNames();
  }

  /**
   * Create a {@link DateTimeFormatInfo} instance appropriate for this locale.
   *
   * Note that the caller takes care of any caching so subclasses need not
   * bother.
   *
   * @return a {@link DateTimeFormatInfo} instance
   */
  public DateTimeFormatInfo getDateTimeFormatInfo() {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.getDateTimeFormatInfo();
  }

  /**
   * Returns the name of the name of the cookie holding the locale to use,
   * which is defined in the config property {@code locale.cookie}.
   * 
   * @return locale cookie name, or null if none
   */
  public String getLocaleCookieName() {
    return null;
  }

  /**
   * Returns the current locale name, such as "default, "en_US", etc.
   */
  public String getLocaleName() {
    return System.getProperty("locale");
  }

  /**
   * Returns the display name of the requested locale in its native locale, if
   * possible. If no native localization is available, the English name will
   * be returned, or as a last resort just the locale name will be returned.  If
   * the locale name is unknown (including user overrides), null is returned.
   *
   * @param localeName the name of the locale to lookup.
   * @return the name of the locale in its native locale
   */
  public String getLocaleNativeDisplayName(String localeName) {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.getLocaleNativeDisplayName(localeName);
  }

  /**
   * Returns the name of the query parameter holding the locale to use, which is
   * defined in the config property {@code locale.queryparam}.
   * 
   * @return locale URL query parameter name, or null if none
   */
  public String getLocaleQueryParam() {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.getLocaleQueryParam();
  }

  /**
   * @return an implementation of {@link LocalizedNames} for this locale.
   */
  public LocalizedNames getLocalizedNames() {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.getLocalizedNames();
  }

  /**
   * Returns a NumberConstants instance appropriate for this locale.
   */
  public NumberConstants getNumberConstants() {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.getNumberConstants();
  }

  /**
   * Returns true if any locale supported by this build of the app is RTL.
   */
  public boolean hasAnyRTL() {
    LocaleInfoImpl localeInfo = org.gwtproject.i18n.client.impl.LocaleInfoImplFactory.get();
    return localeInfo.hasAnyRTL();
    }
}

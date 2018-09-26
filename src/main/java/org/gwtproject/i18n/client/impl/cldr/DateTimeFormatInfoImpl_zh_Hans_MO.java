/*
 * Copyright 2012 Google Inc.
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
package org.gwtproject.i18n.client.impl.cldr;
// DO NOT EDIT - GENERATED FROM CLDR AND ICU DATA

/**
 * Implementation of DateTimeFormatInfo for the "zh_Hans_MO" locale.
 */
public class DateTimeFormatInfoImpl_zh_Hans_MO extends DateTimeFormatInfoImpl_zh_Hans {

  @Override
  public String dateFormatShort() {
    return "d/M/yy";
  }

  @Override
  public String dateTimeFull(String timePattern, String datePattern) {
    return datePattern + timePattern;
  }

  @Override
  public String dateTimeLong(String timePattern, String datePattern) {
    return datePattern + timePattern;
  }

  @Override
  public String formatMonthNumDay() {
    return "M-d";
  }

  @Override
  public String formatYearMonthNum() {
    return "y年M月";
  }

  @Override
  public String formatYearMonthNumDay() {
    return "y年M月d日";
  }
}
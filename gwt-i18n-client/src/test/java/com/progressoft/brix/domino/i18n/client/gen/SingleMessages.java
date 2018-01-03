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
package com.progressoft.brix.domino.i18n.client.gen;

/**
 * Interface to represent the messages contained in resource bundle:
 * 'com/progressoft/brix/domino/i18n/client/gen/SingleMessages.properties'.
 */
public interface SingleMessages extends com.progressoft.brix.domino.i18n.client.Messages {

  /**
   * Translated "me".
   * 
   * @return translated "me"
   */
  @DefaultMessage("me")
  @Key("justOne")
  String justOne();
}

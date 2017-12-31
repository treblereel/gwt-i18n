/*
 * Copyright 2011 Google Inc.
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
package com.progressoft.brix.domino.i18n.server;

/**
 * An exception during processing of i18n messages.
 */
public class MessageProcessingException extends Exception {

  public MessageProcessingException(String msg) {
    super(msg);
  }

  public MessageProcessingException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
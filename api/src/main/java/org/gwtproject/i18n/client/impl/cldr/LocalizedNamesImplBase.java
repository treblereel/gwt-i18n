/*
 * Copyright 2010 Google Inc.
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

import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import org.gwtproject.core.client.JavaScriptObject;
import org.gwtproject.i18n.client.DefaultLocalizedNames;
import org.gwtproject.i18n.client.Localizable;

/**
 * A base class for client-side implementations of the {@link
 * org.gwtproject.i18n.client.LocalizedNames} interface.
 */
public abstract class LocalizedNamesImplBase extends DefaultLocalizedNames
    implements Localizable {

  /**
   * Add all entries in {@code override} to the original map, replacing
   * any existing entries.  This is used by subclasses that need to slightly
   * alter the data used by the parent locale.
   */
  protected static final JavaScriptObject overrideMap(
          JavaScriptObject original, JavaScriptObject override) {
    JsPropertyMap override_ = Js.asPropertyMap(override);
    JsPropertyMap original_ = Js.asPropertyMap(original);

    override_.forEach(key -> {
      if (override_.has(key)) { //TODO check this
        original_.set(key, override_.get(key));
      }
    });

    return Js.uncheckedCast(original_);
  }

  private JavaScriptObject jsoNameMap = null;

  @Override
  public final String getRegionNameImpl(String regionCode) {
    return getRegionNameNative(regionCode);
  }

  @Override
  protected String[] loadLikelyRegionCodes() {
    // If this override isn't here, LocalizableGenerator-produced overrides
    // fail with a visibility error in both javac and eclipse.
    return super.loadLikelyRegionCodes();
  }

  @Override
  protected final void loadNameMap() {
    jsoNameMap = loadNameMapNative();
  }

  /**
   * Load the code=>name map for use in pure Java.  On return, nameMap will be
   * appropriately initialized.
   */
  protected void loadNameMapJava() {
    super.loadNameMap();
  }

  /**
   * Load the code=>name map for use in JS.
   * @return a JSO containing a map of country codes to localized names
   */
  protected abstract JavaScriptObject loadNameMapNative();

  @Override
  protected final boolean needsNameMap() {
    return jsoNameMap == null;
  }

  private String getRegionNameNative(String regionCode) {
    return Js.uncheckedCast(Js.asPropertyMap(this.jsoNameMap).get(regionCode));
  }
}
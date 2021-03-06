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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import org.gwtproject.core.client.JavaScriptObject;

/**
 * Provides dynamic string lookup of key/value string pairs defined in a
 * module's host HTML page. Each unique instance of <code>Dictionary</code> is
 * bound to a named JavaScript object that resides in the global namespace of
 * the host page's window object. The bound JavaScript object is used directly
 * as an associative array.
 *
 * <p>
 * For example, suppose you define the following JavaScript object in your host
 * page:
 *
 * {@gwt.include com/google/gwt/examples/i18n/ThemeDictionaryExample.js}
 *
 * You can then use a <code>Dictionary</code> to access the key/value pairs
 * above:
 *
 * {@example com.google.gwt.examples.i18n.ThemeDictionaryExample#useThemeDictionary()}
 * </p>
 *
 * <p>
 * Unlike the family of interfaces that extend
 * {@link org.gwtproject.i18n.client.Localizable} which support static
 * internationalization, the <code>Dictionary</code> class is fully dynamic.
 * As a result, a variety of error conditions (particularly those involving key
 * mismatches) cannot be caught until runtime. Similarly, the GWT compiler is
 * unable discard unused dictionary values since the structure cannot be
 * statically analyzed.
 * </p>
 *
 * <h3>A Caveat Regarding Locale</h3>
 * The module's host page completely determines the mappings defined for each
 * dictionary without regard to the <code>locale</code> client property. Thus,
 * <code>Dictionary</code> is the most flexible of the internationalization
 * types and may provide the simplest form of integration with existing
 * localization systems which were not specifically designed to use GWT's
 * <code>locale</code> client property.
 *
 * <p>
 * See {@link org.gwtproject.i18n.client.Localizable} for background on the
 * <code>locale</code> client property.
 * </p>
 *
 * <h3>Required Module</h3>
 * Modules that use this interface should inherit
 * <code>org.gwtproject.i18n.I18N</code>.
 *
 * {@gwt.include com/google/gwt/examples/i18n/InheritsExample.gwt.xml}
 */
public final class Dictionary {

  private static Map<String, Dictionary> cache =
    new HashMap<String, Dictionary>();
  private static final int MAX_KEYS_TO_SHOW = 20;

  /**
   * Returns the <code>Dictionary</code> object associated with the given
   * name.
   *
   * @param name
   * @return specified dictionary
   * @throws MissingResourceException
   */
  public static Dictionary getDictionary(String name) {
    Dictionary target = cache.get(name);
    if (target == null) {
      target = new Dictionary(name);
      cache.put(name, target);
    }
    return target;
  }

  static void resourceErrorBadType(String name) {
    throw new MissingResourceException("'" + name
        + "' is not a JavaScript object and cannot be used as a Dictionary",
        null, name);
  }

  private JavaScriptObject dict;

  private String label;

  /**
   * Constructor for <code>Dictionary</code>.
   *
   * @param name name of linked JavaScript Object
   */
  private Dictionary(String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException(
          "Cannot create a Dictionary with a null or empty name");
    }
    this.label = "Dictionary " + name;
    attach(name);
    if (dict == null) {
      throw new MissingResourceException(
          "Cannot find JavaScript object with the name '" + name + "'", name,
          null);
    }
  }

  /**
   * Get the value associated with the given Dictionary key.
   *
   * We have to call Object.hasOwnProperty to verify that the value is
   * defined on this object, rather than a superclass, since normal Object
   * properties are also visible on this object.
   *
   * @param key to lookup
   * @return the value
   * @throws MissingResourceException if the value is not found
   */
  public String get(String key) {
    JsPropertyMap dict = Js.asPropertyMap(this.dict);
    if(!dict.has(key)) {
      resourceError(key);
    }
    return dict.get(key).toString();
  }

  /**
   * The set of keys associated with this dictionary.
   *
   * @return the Dictionary set
   */
  public Set<String> keySet() {
    HashSet<String> s = new HashSet<String>();
    addKeys(s);
    return s;
  }

  @Override
  public String toString() {
    return label;
  }

  /**
   * Collection of values associated with this dictionary.
   *
   * @return the values
   */
  public Collection<String> values() {
    ArrayList<String> s = new ArrayList<String>();
    addValues(s);
    return s;
  }

  void resourceError(String key) {
    String error = "Cannot find '" + key + "' in " + this;
    throw new MissingResourceException(error, this.toString(), key);
  }

  private void addKeys(HashSet<String> s) {
    JsPropertyMap map = Js.asPropertyMap(this.dict);
    map.forEach(key -> {
      if(map.has(key)) { // TODO Check this
        s.add(key);
      }
    });
  }

  private void addValues(ArrayList<String> s) {
    JsPropertyMap map = Js.asPropertyMap(this.dict);
    map.forEach(key -> {
      if(map.has(key)) { // TODO Check this
        s.add(get(key));
      }
    });
  }

  private void attach(String name) {
    try {
      if(Js.asPropertyMap(DomGlobal.window).has(name)){
        if (!(Js.asPropertyMap(DomGlobal.window).get(name) instanceof Object)) {
          resourceErrorBadType(name);
        }
      }
      dict = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get(name));
    } catch (Exception e) {
      resourceErrorBadType(name);
    }
  }
}

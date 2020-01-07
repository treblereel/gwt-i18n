package org.gwtproject.i18n.rg.resource;

import org.gwtproject.i18n.ext.BadPropertyValueException;
import org.gwtproject.i18n.ext.ConfigurationProperty;
import org.gwtproject.i18n.ext.DefaultConfigurationProperty;
import org.gwtproject.i18n.linker.LocalePropertyProviderGenerator;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 11/7/18
 */
public final class ConfigurationProperties {

    public static final String KEY_CLIENT_BUNDLE_CACHE_LOCATION = "ClientBundle.cacheLocation";
    public static final String KEY_LOCALE = "locale";
    public static final String KEY_LOCALE_NEW = "locale.new";
    public static final String KEY_RUNTIME_LOCALES = "runtime.locales";
    public static final String KEY_LOCALE_QUERYPARAM = "locale.queryparam";
    public static final String KEY_LOCALE_COOKIE = "locale.cookie";
    public static final String KEY_GWT_FORCEBIDI_NEW = "gwt.forceBidi.new";
    public static final String KEY_CLIENT_BUNDLE_CACHE_URL = "ClientBundle.cacheUrl";

    public static final String KEY_LOCALE_NEW_SEARCHORDER = "locale.new.searchorder";
    private static final String KEY_CLIENT_LOCALE_NEW_USEMETA = "locale.new.usemeta";
    private static final String KEY_CLIENT_LOCALE_NEW_USERAGENT = "locale.new.useragent";
    private static final String KEY_CLIENT_LOCALE_NEW_COOKIE = "locale.new.cookie";
    private static final String KEY_CLIENT_LOCALE_NEW_QUERYPARAM = "locale.new.queryparam";


    private static final String CLIENT_BUNDLE_DEFAULT_CACHE_LOCATION = "src/main/webapp/gwt-cache";
    private static final String CLIENT_BUNDLE_DEFAULT_CACHE_URL = "/gwt-cache/";

    private final Map<String, ConfigurationProperty> holder = new HashMap<>();
    private final Filer filer;

    public ConfigurationProperties(Filer filer) {
        this.filer = filer;
        setDefaultProperties();
        setGWTCacheLocation();
    }

    private void setDefaultProperties() {
        lookupAndSet(KEY_RUNTIME_LOCALES, Arrays.asList("en"), true);
        lookupAndSet(KEY_GWT_FORCEBIDI_NEW, Arrays.asList("default"), true);
        lookupAndSet(KEY_LOCALE_NEW, Arrays.asList("false"), true);
        lookupAndSet(KEY_LOCALE, Arrays.asList("default"), true);
        lookupAndSet(KEY_LOCALE_NEW_SEARCHORDER, Arrays.asList("queryparam","cookie","meta","useragent"), true);
        lookupAndSet(KEY_LOCALE_QUERYPARAM, Arrays.asList("locale"), true);
        lookupAndSet(KEY_LOCALE_COOKIE, Arrays.asList(""), true);
        lookupAndSet(KEY_CLIENT_BUNDLE_CACHE_URL, Arrays.asList(CLIENT_BUNDLE_DEFAULT_CACHE_URL), true);

        lookupAndSet(KEY_CLIENT_LOCALE_NEW_USEMETA, Arrays.asList("Y"), true);
        lookupAndSet(KEY_CLIENT_LOCALE_NEW_USERAGENT, Arrays.asList("N"), true);
        lookupAndSet(KEY_CLIENT_LOCALE_NEW_COOKIE, Arrays.asList("N"), true);
        lookupAndSet(KEY_CLIENT_LOCALE_NEW_QUERYPARAM, Arrays.asList("N"), true);
    }

    private void lookupAndSet(String propertyName, List<String> defaultValues, boolean override) {
        List<String> list = new ArrayList<>(defaultValues);
        holder.put(propertyName, lookup(
                new DefaultConfigurationProperty(propertyName, list), override));
    }

    private ConfigurationProperty lookup(ConfigurationProperty holder, boolean override) {
        String values = System.getProperty(holder.getName());
        if (values != null) {
            if (override) {
                holder.getValues().clear();
            }
            Arrays.stream(values.split("\\s+")).forEach(e -> holder.getValues().add(e));
        }
        return holder;
    }

    private void setGWTCacheLocation() {
        File gwtCacheDir;
        try {
            FileObject fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "dummy" + System.currentTimeMillis());

            Path path = Paths.get(fileObject.toUri())
                    .getParent()  // {PROJECT_ROOT}/target/generated-sources/annotations
                    .getParent()  // {PROJECT_ROOT}/target/generated-sources
                    .getParent()  // {PROJECT_ROOT}/target
                    .getParent(); // {PROJECT_ROOT}

            String cacheLocation = System.getProperty(KEY_CLIENT_BUNDLE_CACHE_LOCATION);
            if (cacheLocation != null) {
                gwtCacheDir = new File(cacheLocation);
            } else {
                gwtCacheDir = new File(path.toUri().getPath() + CLIENT_BUNDLE_DEFAULT_CACHE_LOCATION);
            }

            if (!gwtCacheDir.exists()) {
                gwtCacheDir.mkdirs();
            }
            holder.put(KEY_CLIENT_BUNDLE_CACHE_LOCATION,
                    new DefaultConfigurationProperty(KEY_CLIENT_BUNDLE_CACHE_LOCATION, Arrays.asList(gwtCacheDir.toString())));

        } catch (IOException e) {
            throw new Error("Unable to locate gwt cache folder " + e.getMessage());
        }
    }

    public ConfigurationProperty getConfigurationProperty(String key) throws BadPropertyValueException {
        if (holder.containsKey(key)) {
            return holder.get(key);
        }
        throw new BadPropertyValueException(key);
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.gwtproject.i18n.rg.rebind;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import com.google.auto.common.MoreElements;
import org.apache.tapestry.util.text.LocalizedProperties;
import org.gwtproject.core.client.JavaScriptObject;
import org.gwtproject.i18n.client.impl.LocaleInfoImpl;
import org.gwtproject.i18n.client.impl.cldr.DateTimeFormatInfoImpl;
import org.gwtproject.i18n.ext.GeneratorContext;
import org.gwtproject.i18n.ext.PropertyOracle;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.rg.Generator;
import org.gwtproject.i18n.rg.resource.impl.ResourceLocatorImpl;
import org.gwtproject.i18n.rg.util.SourceWriter;
import org.gwtproject.i18n.server.CodeGenUtils;
import org.gwtproject.i18n.server.GwtLocaleImpl;
import org.gwtproject.i18n.shared.GwtLocale;

public class LocaleInfoGenerator extends Generator {

    /**
     * Properties file containing machine-generated locale display names, in their
     * native locales (if possible).
     */
    private static final String GENERATED_LOCALE_NATIVE_DISPLAY_NAMES = "org/gwtproject/i18n/client/impl/cldr/LocaleNativeDisplayNames-generated.properties";

    /**
     * Properties file containing hand-made corrections to the machine-generated
     * locale display names above.
     */
    private static final String MANUAL_LOCALE_NATIVE_DISPLAY_NAMES = "org/gwtproject/i18n/client/impl/cldr/LocaleNativeDisplayNames-manual.properties";

    /**
     * Properties file containing hand-made overrides of locale display names, in
     * their native locales (if possible).
     */
    private static final String OVERRIDE_LOCALE_NATIVE_DISPLAY_NAMES = "org/gwtproject/i18n/client/impl/cldr/LocaleNativeDisplayNames-override.properties";

    /**
     * Set of canonical language codes which are RTL.
     */
    private static final Set<String> RTL_LOCALES = new HashSet<>();

    static {
        // TODO(jat): get this from CLDR data.
        RTL_LOCALES.add("ar");
        RTL_LOCALES.add("fa");
        RTL_LOCALES.add("he");
        RTL_LOCALES.add("ps");
        RTL_LOCALES.add("ur");
    }

    /**
     * Generate an implementation for the given type.
     * @param logger error logger
     * @param context generator context
     * @param typeName target type name
     * @return generated class name
     * @throws UnableToCompleteException
     */
    @Override
    public final String generate(TreeLogger logger, final GeneratorContext context,
                                 String typeName) throws UnableToCompleteException {
        // Get the current locale and interface type.
        PropertyOracle propertyOracle = context.getPropertyOracle();
        LocaleUtils localeUtils = LocaleUtils.getInstance(logger, propertyOracle,
                                                          context);
        TypeElement targetClass = context.getAptContext().elements.getTypeElement(typeName);
        assert (LocaleInfoImpl.class.getName().equals(
                targetClass.getQualifiedName().toString()));

        String packageName = MoreElements.getPackage(targetClass).getQualifiedName().toString();
        String superClassName = targetClass.getSimpleName().toString().replace('.', '_') + "_shared";

        Set<GwtLocale> localeSet = localeUtils.getAllLocales();
        GwtLocaleImpl[] allLocales = localeSet.toArray(
                new GwtLocaleImpl[localeSet.size()]);
        // sort for deterministic output
        Arrays.sort(allLocales);
        PrintWriter pw = context.tryCreate(logger, packageName, superClassName);
        if (pw != null) {
            LocalizedProperties displayNames = new LocalizedProperties();
            LocalizedProperties displayNamesManual = new LocalizedProperties();
            LocalizedProperties displayNamesOverride = new LocalizedProperties();
            try {
                InputStream str = ResourceLocatorImpl.tryFindResourceAsStream(logger,
                                                                              context.getResourcesOracle(), GENERATED_LOCALE_NATIVE_DISPLAY_NAMES);
                if (str != null) {
                    displayNames.load(str, "UTF-8");
                }
                str = ResourceLocatorImpl.tryFindResourceAsStream(logger, context.getResourcesOracle(),
                                                                  MANUAL_LOCALE_NATIVE_DISPLAY_NAMES);
                if (str != null) {
                    displayNamesManual.load(str, "UTF-8");
                }
                str = ResourceLocatorImpl.tryFindResourceAsStream(logger, context.getResourcesOracle(),
                                                                  OVERRIDE_LOCALE_NATIVE_DISPLAY_NAMES);
                if (str != null) {
                    displayNamesOverride.load(str, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                // UTF-8 should always be defined
                logger.log(TreeLogger.ERROR, "UTF-8 encoding is not defined", e);
                throw new UnableToCompleteException();
            } catch (IOException e) {
                logger.log(TreeLogger.ERROR, "Exception reading locale display names",
                           e);
                throw new UnableToCompleteException();
            }

            ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
                    packageName, superClassName);
            factory.setSuperclass(targetClass.getQualifiedName().toString());
            factory.addImport(JavaScriptObject.class.getCanonicalName());
            factory.addImport(HashMap.class.getCanonicalName());
            SourceWriter writer = factory.createSourceWriter(context, pw);
            writer.println();
            writer.println("HashMap<String,String> nativeDisplayNamesJava;");
            writer.println();
            writer.println("@Override");
            writer.println("public String[] getAvailableLocaleNames() {");
            writer.println("  return new String[] {");
            boolean hasAnyRtl = genGetAvailableLocaleNames(localeUtils, allLocales, writer);
            writer.println("  };");
            writer.println("}");
            writer.println();
            writer.println("@Override");
            writer.println("public String getLocaleNativeDisplayName(String localeName) {");
            writer.println("  if (nativeDisplayNamesJava == null) {");
            writer.println("    nativeDisplayNamesJava = new HashMap<String, String>();");
            getGetLocaleNativeDisplayName(allLocales, displayNames, displayNamesManual, displayNamesOverride, writer);
            writer.println("  }");
            writer.println("  return nativeDisplayNamesJava.get(localeName);");
            writer.println("}");
            writer.println();
            writer.println("@Override");
            writer.println("public boolean hasAnyRTL() {");
            writer.println("  return " + hasAnyRtl + ";");
            writer.println("}");
            writer.println();
            writer.commit(logger);
        }

        generateImpl(logger, context, localeUtils, targetClass, packageName, superClassName, allLocales);
        new ImplFactoryGenerator(logger, context, targetClass, localeUtils).generate();
        return targetClass.getQualifiedName().toString();
    }

    private void getGetLocaleNativeDisplayName(GwtLocaleImpl[] allLocales, LocalizedProperties displayNames, LocalizedProperties displayNamesManual, LocalizedProperties displayNamesOverride, SourceWriter writer) {
        for (GwtLocaleImpl possibleLocale : allLocales) {
            String localeName = possibleLocale.toString();
            String displayName = displayNamesOverride.getProperty(localeName);
            if (displayName == null) {
                displayName = displayNamesManual.getProperty(localeName);
            }
            if (displayName == null) {
                displayName = displayNames.getProperty(localeName);
            }
            if (displayName != null && displayName.length() != 0) {
                writer.println("      nativeDisplayNamesJava.put("
                                       + CodeGenUtils.asStringLiteral(localeName) + ", "
                                       + CodeGenUtils.asStringLiteral(displayName) + ");");
            }
        }
    }

    private boolean genGetAvailableLocaleNames(LocaleUtils localeUtils, GwtLocaleImpl[] allLocales, SourceWriter writer) {
        boolean hasAnyRtl = false;

        hasAnyRtl = genGetAvailableLocaleName(writer, hasAnyRtl, (GwtLocaleImpl) localeUtils.getDefaultLocale());

        for (GwtLocaleImpl possibleLocale : allLocales) {
            hasAnyRtl = genGetAvailableLocaleName(writer, hasAnyRtl, possibleLocale);
        }
        return hasAnyRtl;
    }

    private boolean genGetAvailableLocaleName(SourceWriter writer, boolean hasAnyRtl, GwtLocaleImpl possibleLocale) {
        writer.println("    \""
                               + possibleLocale.toString().replaceAll("\"", "\\\"") + "\",");
        if (RTL_LOCALES.contains(
                possibleLocale.getCanonicalForm().getLanguage())) {
            hasAnyRtl = true;
        }
        return hasAnyRtl;
    }

    private void generateImpl(TreeLogger logger, GeneratorContext context, LocaleUtils localeUtils, TypeElement targetClass, String packageName, String superClassName, GwtLocaleImpl[] allLocales) throws UnableToCompleteException {
        for (GwtLocaleImpl locale : allLocales) {
            generateImpl(logger, context, localeUtils, targetClass, packageName, superClassName, locale);
        }
        generateImpl(logger, context, localeUtils, targetClass, packageName, superClassName, ((GwtLocaleImpl) localeUtils.getDefaultLocale()));
    }

    private void generateImpl(TreeLogger logger, GeneratorContext context, LocaleUtils localeUtils, TypeElement targetClass, String packageName, String superClassName, GwtLocaleImpl locale) throws UnableToCompleteException {
        PrintWriter pw;
        String className = targetClass.getSimpleName().toString().replace('.', '_') + "_"
                + locale.getAsString();
        Set<GwtLocale> runtimeLocales = localeUtils.getRuntimeLocales();
        if (!runtimeLocales.isEmpty()) {
            className += "_runtimeSelection";
        }
        pw = context.tryCreate(logger, packageName, className);
        if (pw != null) {
            ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
                    packageName, className);
            factory.setSuperclass(superClassName);
            factory.addImport("org.gwtproject.i18n.client.LocaleInfo");
            factory.addImport("org.gwtproject.i18n.client.constants.NumberConstants");
            factory.addImport("org.gwtproject.i18n.client.constants.NumberConstantsImpl");
            factory.addImport("org.gwtproject.i18n.client.DateTimeFormatInfo");
            factory.addImport("org.gwtproject.i18n.client.impl.cldr.DateTimeFormatInfoImpl");
            SourceWriter writer = factory.createSourceWriter(context, pw);
            writer.println("@Override");
            writer.println("public String getLocaleName() {");
            if (runtimeLocales.isEmpty()) {
                writer.println("  return \"" + locale + "\";");
            } else {
                writer.println("  String rtLocale = getRuntimeLocale();");
                writer.println("  return rtLocale != null ? rtLocale : \"" + locale
                                       + "\";");
            }
            writer.println("}");
            writer.println();
            String queryParam = localeUtils.getQueryParam();
            if (queryParam != null) {
                writer.println("@Override");
                writer.println("public String getLocaleQueryParam() {");
                writer.println("  return " + CodeGenUtils.asStringLiteral(queryParam) + ";");
                writer.println("}");
                writer.println();
            }
            String cookie = localeUtils.getCookie();
            if (cookie != null) {
                writer.println("@Override");
                writer.println("public String getLocaleCookieName() {");
                writer.println("  return " + CodeGenUtils.asStringLiteral(cookie) + ";");
                writer.println("}");
                writer.println();
            }
            writer.println("@Override");
            writer.println("public DateTimeFormatInfo getDateTimeFormatInfo() {");
            LocalizableGenerator localizableGenerator = new LocalizableGenerator();
            generateConstantsLookup(logger, context, writer, localizableGenerator,
                                    runtimeLocales, localeUtils, locale,
                                    "org.gwtproject.i18n.client.impl.cldr.DateTimeFormatInfoImpl");
            writer.println("}");
            writer.println();
            writer.println("@Override");
            writer.println("public NumberConstants getNumberConstants() {");
            generateConstantsLookup(logger, context, writer, localizableGenerator,
                                    runtimeLocales, localeUtils, locale,
                                    "org.gwtproject.i18n.client.constants.NumberConstantsImpl");
            writer.println("}");
            writer.commit(logger);
        }
    }

    /**
     * @param logger
     * @param context
     * @param writer
     * @param localizableGenerator
     * @param runtimeLocales
     * @param localeUtils
     * @param locale
     * @throws UnableToCompleteException
     */
    private void generateConstantsLookup(TreeLogger logger,
                                         GeneratorContext context, SourceWriter writer,
                                         LocalizableGenerator localizableGenerator, Set<GwtLocale> runtimeLocales,
                                         LocaleUtils localeUtils, GwtLocale locale, String typeName)
            throws UnableToCompleteException {
        writer.indent();
        boolean fetchedRuntimeLocale = false;
        Map<String, Set<GwtLocale>> localeMap = new HashMap<>();
        generateOneLocale(logger, context, localizableGenerator, typeName,
                          localeUtils, localeMap, locale);
        for (GwtLocale runtimeLocale : runtimeLocales) {
            generateOneLocale(logger, context, localizableGenerator, typeName,
                              localeUtils, localeMap, runtimeLocale);
        }
        if (localeMap.size() > 1) {
            for (Entry<String, Set<GwtLocale>> entry : localeMap.entrySet()) {
                if (!fetchedRuntimeLocale) {
                    writer.println("String runtimeLocale = getLocaleName();");
                    fetchedRuntimeLocale = true;
                }
                writer.print("if (");
                boolean firstLocale = true;
                String generatedClass = entry.getKey();
                for (GwtLocale runtimeLocale : entry.getValue()) {
                    if (firstLocale) {
                        firstLocale = false;
                    } else {
                        writer.println();
                        writer.print("    || ");
                    }
                    writer.print("\"" + runtimeLocale.toString()
                                         + "\".equals(runtimeLocale)");
                }
                writer.println(") {");
                writer.println("  return new " + generatedClass + getDefaultOrLocale(generatedClass, locale) + "();");
                writer.println("}");
            }
            // TODO: if we get here, there was an unexpected runtime locale --
            //    should we have an assert or throw an exception?  Currently it
            //    just falls through to the default implementation.
        }
        writer.println("return new " + typeName + getDefaultOrLocale(typeName, locale) + "();");
        writer.outdent();
    }

    private String getDefaultOrLocale(String type, GwtLocale locale) {

        return locale.toString().equals("default") ?
                (type.contains(DateTimeFormatInfoImpl.class.getCanonicalName())
                        ? "" : "_")
                : "_" + locale.toString();
    }

    /**
     * @param logger
     * @param context
     * @param localizableGenerator
     * @param typeName
     * @param localeUtils
     * @param localeMap
     * @param locale
     * @throws UnableToCompleteException
     */
    private void generateOneLocale(TreeLogger logger, GeneratorContext context,
                                   LocalizableGenerator localizableGenerator, String typeName,
                                   LocaleUtils localeUtils, Map<String, Set<GwtLocale>> localeMap, GwtLocale locale)
            throws UnableToCompleteException {
        String generatedClass = localizableGenerator.generate(logger, context,
                                                              typeName, localeUtils, locale);
        if (generatedClass == null) {
            logger.log(TreeLogger.ERROR, "Failed to generate " + typeName
                    + " in locale " + locale.toString());
            // skip failed locale
            return;
        }
        Set<GwtLocale> locales = localeMap.get(generatedClass);
        if (locales == null) {
            locales = new HashSet<>();
            localeMap.put(generatedClass, locales);
        }
        locales.add(locale);
    }
}

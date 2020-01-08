package org.gwtproject.i18n.rg.rebind;

import java.io.PrintWriter;

import org.gwtproject.i18n.ext.GeneratorContext;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.rg.util.SourceWriter;
import org.gwtproject.i18n.server.CodeGenUtils;
import org.gwtproject.i18n.shared.GwtLocale;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 1/8/20
 */
public class LocaleInfoImplFactoryGenerator {

    private final TreeLogger logger;
    private final GeneratorContext context;
    private final LocaleUtils localeUtils;

    LocaleInfoImplFactoryGenerator(TreeLogger logger,
                                   GeneratorContext context,
                                   LocaleUtils localeUtils) {
        this.logger = logger;
        this.context = context;
        this.localeUtils = localeUtils;
    }

    void generate() throws UnableToCompleteException {
        String className = "LocaleInfoImplFactory";
        String packageName = "org.gwtproject.i18n.client.impl";

        PrintWriter pw = context.tryCreate(logger, packageName, className);
        if (pw != null) {
            ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
                    packageName, className);
            factory.addImport("java.util.Map");
            factory.addImport("java.util.HashMap");
            SourceWriter writer = factory.createSourceWriter(context, pw);
            writer.println("private Map<String, LocaleInfoImpl> holder = new HashMap<>();");
            writer.println();
            writer.println("private static LocaleInfoImplFactory instance;");
            writer.println();

            writer.println("private LocaleInfoImplFactory() {");
            localeUtils.getAllLocales().forEach(locale -> generateHolderEntry(writer, locale));
            generateHolderEntry(writer, localeUtils.getDefaultLocale());
            writer.println("}");
            writer.println();

            writer.println("public static LocaleInfoImpl get() {");
            writer.println("    String locale = System.getProperty(\"locale\");");
            writer.println("    return LocaleInfoImplFactory.getFactory().getLocaleInfoImpl(locale);");
            writer.println("}");
            writer.println();

            writer.println("public static LocaleInfoImplFactory getFactory() {");
            writer.println("    if(instance == null) {");
            writer.println("       instance = new LocaleInfoImplFactory();");
            writer.println("    }");
            writer.println("    return instance;");
            writer.println("}");
            writer.println();

            writer.println("public LocaleInfoImpl getLocaleInfoImpl(String locale) {");
            writer.println("    if(holder.containsKey(locale)) {");
            writer.println("        return holder.get(locale);");
            writer.println("    }");
            writer.println("    return holder.get(\"default\");");
            writer.println("}");
            writer.println();
            writer.commit(logger);
        }
    }

    private void generateHolderEntry(SourceWriter writer, GwtLocale locale) {
        writer.print("  holder.put(");
        writer.print(CodeGenUtils.asStringLiteral(locale.toString()));
        writer.print(", new LocaleInfoImpl_");
        writer.print(locale.toString().equals("default") ? "" : locale.toString());
        writer.print("());");
        writer.println();
    }
}

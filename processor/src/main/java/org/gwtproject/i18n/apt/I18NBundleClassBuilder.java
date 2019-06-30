package org.gwtproject.i18n.apt;

import java.util.Set;

import javax.lang.model.element.TypeElement;

import org.gwtproject.i18n.context.AptContext;
import org.gwtproject.i18n.ext.StandardGeneratorContext;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.rg.rebind.LocalizableGenerator;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 6/30/19
 */
public class I18NBundleClassBuilder {
    private final TreeLogger logger;
    private final AptContext context;

    private final Set<TypeElement> elements;

    public I18NBundleClassBuilder(TreeLogger logger, AptContext context, Set<TypeElement> elements) {
        this.logger = logger;
        this.context = context;
        this.elements = elements;
    }

    public void process() throws UnableToCompleteException {
        StandardGeneratorContext standardGeneratorContext = new StandardGeneratorContext(context);
        LocalizableGenerator gen = new LocalizableGenerator();
        for (TypeElement element : elements) {
            gen.generate(logger, standardGeneratorContext, element.getQualifiedName().toString());
        }
    }
}

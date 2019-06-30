package org.gwtproject.i18n.context;

import javax.lang.model.element.TypeElement;

import org.gwtproject.i18n.ext.GeneratorContext;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.rg.rebind.ResourceFactoryContext;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 6/30/19
 */
public class I18NClientBundleGenerator extends AbstractClientBundleGenerator {

    private final ResourceFactoryContext clientBundleCtx = new ResourceFactoryContext();

    @Override
    protected AbstractResourceContext createResourceContext(TreeLogger logger, GeneratorContext context, TypeElement resourceBundleType) throws UnableToCompleteException {
        return null;
    }

    @Override
    public String generate(TreeLogger var1, GeneratorContext var2, String var3) throws UnableToCompleteException {
        return null;
    }
}

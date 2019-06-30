package org.gwtproject.i18n.context;

import org.gwtproject.i18n.client.Constants;
import org.gwtproject.i18n.client.Messages;
import org.gwtproject.i18n.ext.ResourceGenerator;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 10/26/18.
 */
public class AptContext {
    public final Messager messager;
    public final Filer filer;
    public final Elements elements;
    public final Types types;
    public final RoundEnvironment roundEnvironment;
    public final ProcessingEnvironment processingEnv;

    public final Map<Element, Class<? extends ResourceGenerator>> generators = new HashMap<>();

    public AptContext(final ProcessingEnvironment processingEnv, final RoundEnvironment roundEnvironment) {
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        this.elements = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        this.roundEnvironment = roundEnvironment;

        this.processingEnv = processingEnv;
        initGenerators();
    }

    private void initGenerators() {
        preBuildGenerators();
    }

    private void preBuildGenerators() {
        //generators.put(elements.getTypeElement(Messages.class.getCanonicalName()), BundleResourceGenerator.class);
        //generators.put(elements.getTypeElement(Constants.class.getCanonicalName()), CssResourceGenerator.class);
    }

}
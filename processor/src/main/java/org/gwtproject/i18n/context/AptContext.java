package org.gwtproject.i18n.context;

import org.gwtproject.i18n.client.*;
import org.gwtproject.i18n.ext.ResourceGenerator;
import org.gwtproject.i18n.ext.ResourceGeneratorType;
import org.gwtproject.i18n.rg.*;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
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
        userDefinedGenerators();
    }

    private void preBuildGenerators() {
        generators.put(elements.getTypeElement(ClientBundle.class.getCanonicalName()), BundleResourceGenerator.class);
        generators.put(elements.getTypeElement(CssResource.class.getCanonicalName()), CssResourceGenerator.class);
        generators.put(elements.getTypeElement(DataResource.class.getCanonicalName()), DataResourceGenerator.class);
        generators.put(elements.getTypeElement(ExternalTextResource.class.getCanonicalName()), ExternalTextResourceGenerator.class);
        generators.put(elements.getTypeElement(ImageResource.class.getCanonicalName()), ImageResourceGenerator.class);
        generators.put(elements.getTypeElement(TextResource.class.getCanonicalName()), TextResourceGenerator.class);
    }

    private void userDefinedGenerators() {
        roundEnvironment.getElementsAnnotatedWith(ResourceGeneratorType.class).forEach(e -> {
            ResourceGeneratorType resourceGeneratorType = e.getAnnotation(ResourceGeneratorType.class);
            String resourceGeneratorName = getResourceGeneratorType(resourceGeneratorType).toString();
           try {
                generators.put(e, (Class<? extends ResourceGenerator>) Class.forName(resourceGeneratorName));
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                throw new Error(e1);
            }
        });
    }

    private TypeMirror getResourceGeneratorType(ResourceGeneratorType annotation) {
        try {
            annotation.value();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

}
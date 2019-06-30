package org.gwtproject.i18n.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import org.gwtproject.i18n.context.AptContext;
import org.gwtproject.i18n.ext.TreeLogger;
import org.gwtproject.i18n.ext.UnableToCompleteException;
import org.gwtproject.i18n.logger.PrintWriterTreeLogger;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 6/30/19
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"org.gwtproject.i18n.client.I18N"})
public class I18NAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }

        AptContext context = new AptContext(processingEnv, roundEnvironment);
        TreeLogger logger = new PrintWriterTreeLogger();
        ((PrintWriterTreeLogger) logger).setMaxDetail(TreeLogger.Type.INFO);
        for (TypeElement annotation : annotations) {
            Set<TypeElement> elements = (Set<TypeElement>) roundEnvironment.getElementsAnnotatedWith(annotation);
            try {
                new I18NBundleClassBuilder(logger, context, elements).process();
            } catch (UnableToCompleteException e) {
                throw new Error(e);
            }
        }
        return true;
    }

}

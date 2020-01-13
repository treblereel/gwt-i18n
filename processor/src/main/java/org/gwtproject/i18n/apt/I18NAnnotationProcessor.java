package org.gwtproject.i18n.apt;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.gwtproject.i18n.client.I18N;
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

    private final Set<TypeElement> resources = new HashSet<>();

    private AptContext context;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }
        context = new AptContext(processingEnv, roundEnvironment);
        TreeLogger logger = new PrintWriterTreeLogger();
        ((PrintWriterTreeLogger) logger).setMaxDetail(TreeLogger.Type.INFO);
        for (TypeElement annotation : annotations) {
            findResources((Set<TypeElement>) roundEnvironment.getElementsAnnotatedWith(annotation));
            try {
                new I18NBundleClassBuilder(logger, context, resources).process();
            } catch (UnableToCompleteException e) {
                throw new Error(e);
            }
        }
        return true;
    }

    private void findResources(Set<TypeElement> elements) {
        resources.addAll(elements);
        //resources.addAll(processExternalResources());
    }

    private Set<TypeElement> processExternalResources() {
        Set<TypeElement> resources = new HashSet<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().scan()) {
            ClassInfoList routeClassInfoList = scanResult.getAllInterfaces();
            for (ClassInfo routeClassInfo : routeClassInfoList) {
                if(routeClassInfo.getName().contains("org.gwtproject.i18n.client.constants")) {
                    resources.add(context.elements.getTypeElement(routeClassInfo.getName()));
                }
            }
        }
        return resources;
    }
}

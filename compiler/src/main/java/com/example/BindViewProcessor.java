package com.example;

import com.delta.annotationmodule.BindView;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/5/15 15:32
 */
@SupportedAnnotationTypes("com.delta.annotationmodule.BindView")
public class BindViewProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elementsAnnotatedWith) {
            messager.printMessage(Diagnostic.Kind.NOTE,element.toString());
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;

            }
        }
        return false;
    }
}

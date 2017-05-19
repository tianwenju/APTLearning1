package com.example;

import com.delta.annotationmodule.Test;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SupportedAnnotationTypes("com.delta.annotationmodule.Test")
//@SupportedSourceVersion(SourceVersion.RELEASE_6)
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    //设置处理制定的AnnotationTypes
    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return Collections.singleton(Test.class.getCanonicalName());
//    }

    String ss="";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
//        Map<String, String> options = processingEnvironment.getOptions();
//        Set<Map.Entry<String, String>> entries = options.entrySet();
//        for (Map.Entry<String, String> entry : entries) {
//            ss = entry.getValue() + ss;
//            messager.printMessage(Diagnostic.Kind.NOTE, entry.getKey() + "----" + entry.getValue());
//        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for (TypeElement typeElement : set) {
            System.out.print(typeElement.getQualifiedName().toString())
            ;
        }

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Test.class);

        for (Element element : elementsAnnotatedWith) {

            if(element.getKind()== ElementKind.CLASS){

                TypeElement typeElement = (TypeElement) element;
                Name qualifiedName = typeElement.getQualifiedName();
                PackageElement packageOf = elementUtils.getPackageOf(typeElement);
                String packageName = packageOf.getQualifiedName().toString();
                ClassName activityName = ClassName.bestGuess(typeElement.getSimpleName().toString());
                MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, ss)
                        .build();
                TypeSpec helloWorld = TypeSpec.classBuilder(activityName+"$$helloWorld")
                        .addModifiers(Modifier.PUBLIC).addMethod(main).build();
                JavaFile javaFile = JavaFile.builder(packageName, helloWorld).build();
                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return false;
    }
}

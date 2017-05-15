package com.example;

import com.delta.annotationmodule.Test;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

//@SupportedAnnotationTypes("com.delta.annotationmodule.Test")
//@SupportedSourceVersion(SourceVersion.RELEASE_6)
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    //设置处理制定的AnnotationTypes
    private Filer filer;
    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());
    }

    String ss="";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        Map<String, String> options = processingEnvironment.getOptions();
        Set<Map.Entry<String, String>> entries = options.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            ss = entry.getValue() + ss;
            messager.printMessage(Diagnostic.Kind.NOTE, entry.getKey() + "----" + entry.getValue());
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, ss)
                .build();
        TypeSpec helloWorld = TypeSpec.classBuilder("helloWorld")
                .addModifiers(Modifier.PUBLIC).addMethod(main).build();
        JavaFile javaFile = JavaFile.builder("com.example.helloWorld", helloWorld).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

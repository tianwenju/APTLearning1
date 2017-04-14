package com.example;

import com.delta.annotationmodule.Test;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

//@SupportedAnnotationTypes("com.delta.annotationmodule.Test")
//@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TestProcessor extends AbstractProcessor {

    //设置处理制定的AnnotationTypes
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class,"args")
                .addStatement("$T.out.println($S)",System.class,"hello,delta,i'm apt")
                .build();
        TypeSpec helloWorld=TypeSpec.classBuilder("helloWorld")
                .addModifiers(Modifier.PUBLIC).addMethod(main).build();
        JavaFile javaFile=JavaFile.builder("com.example.helloWorld",helloWorld).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

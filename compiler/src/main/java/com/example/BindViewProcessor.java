package com.example;

import com.delta.annotationmodule.BindView;
import com.delta.annotationmodule.ViewBinder;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/5/15 15:32
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.delta.annotationmodule.BindView")
public class BindViewProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Messager messager;
    Map<TypeElement, List<FieldBinding>> map = new HashMap<>();
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        super.init(processingEnvironment);
    }
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elementsAnnotatedWith) {
            messager.printMessage(Diagnostic.Kind.NOTE, element.toString());
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement var = (VariableElement) element;
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                List<FieldBinding> fieldBindings = map.get(typeElement);
                if (fieldBindings == null) {
                    fieldBindings = new ArrayList<>();
                    map.put(typeElement, fieldBindings);
                }
                BindView annotation = element.getAnnotation(BindView.class);
                int resId = annotation.value();
                String fileName = element.getSimpleName().toString();
                TypeMirror typeMirror = element.asType();
                messager.printMessage(Diagnostic.Kind.NOTE, fileName + "" + typeMirror.getKind());
                FieldBinding fieldBinding1 = new FieldBinding(fileName, typeMirror, resId);
                fieldBindings.add(fieldBinding1);

                for (Map.Entry<TypeElement, List<FieldBinding>> elementListEntry : map.entrySet()) {

                    TypeElement activityTypeElement = elementListEntry.getKey();
                    String packageName = elementUtils.getPackageOf(activityTypeElement).getQualifiedName().toString();
                    String activityName = activityTypeElement.getSimpleName().toString();

                    ClassName activityClassName = ClassName.bestGuess(activityName);
                    MethodSpec.Builder method = MethodSpec.methodBuilder("bind")
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addAnnotation(Override.class)
                            .addParameter(activityClassName, "target");
                    List<FieldBinding> fieldBindingList = elementListEntry.getValue();
                    for (FieldBinding fieldBinding : fieldBindingList) {

                        String type_name = fieldBinding.getTypeMirror().toString();
                        ClassName fieldTypeName = ClassName.bestGuess(type_name);
                        method.addStatement("target.$L=($T)target.findViewById($L)", fieldBinding.getName(), fieldTypeName, fieldBinding.getResId());
                    }

                   // ClassName InterfaceClassName = ClassName.get(ViewBinder.class.getPackage().getName(), ViewBinder.class.getSimpleName());
                    ClassName InterfaceClassName = ClassName.get(ViewBinder.class);
                    TypeSpec.Builder result = TypeSpec.classBuilder(activityClassName + "$$ViewBinder")
                            .addModifiers(Modifier.PUBLIC)
                            .addTypeVariable(TypeVariableName.get("T", activityClassName))
                            .addSuperinterface(ParameterizedTypeName.get(InterfaceClassName, activityClassName))
                            .addMethod(method.build());
                    try {
                        JavaFile.builder(packageName, result.build()).build().writeTo(filer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }


        }
        return false;
    }
}

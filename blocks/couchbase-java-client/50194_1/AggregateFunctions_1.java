package com.couchbase.client.preprocessing.query;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class QueryDslGenerationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GenerateDsl.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_6;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateDsl.class)) {
            if (element.getKind() == ElementKind.METHOD) {

                MethodSpec greetCustomer = MethodSpec.methodBuilder("greetCustomer").addModifiers(Modifier.PUBLIC).returns(String.class).addParameter(String.class, "name")
                                                     .addStatement("return $S+$N", "Welcome, ", "name").build();
                TypeSpec customerService = TypeSpec.classBuilder("CustomerService").addModifiers(Modifier.PUBLIC).addMethod(greetCustomer).build();
                JavaFile javaFile = JavaFile.builder("com.hashcode.tutorial", customerService).build();


                try {
                    JavaFileObject sourceFile = processingEnv.getFiler()
                         .createSourceFile("com.hashcode.tutorial.CustomerService", element);

                    Writer sourceWriter = sourceFile.openWriter();
                    javaFile.writeTo(sourceWriter);
                    sourceWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}

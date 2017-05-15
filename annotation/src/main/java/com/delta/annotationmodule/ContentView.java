package com.delta.annotationmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/5/15 15:34
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView
{
    int value();
}
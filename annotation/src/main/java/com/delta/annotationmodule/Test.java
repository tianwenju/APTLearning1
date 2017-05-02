package com.delta.annotationmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/14 14:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Test {
}

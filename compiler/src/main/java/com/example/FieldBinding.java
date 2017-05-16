package com.example;

import javax.lang.model.type.TypeMirror;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/5/16 13:21
 */


public class FieldBinding {

    private String name;
    private TypeMirror typeMirror;
    private int resId;

    public FieldBinding(String name, TypeMirror typeMirror, int resId) {
        this.name = name;
        this.typeMirror = typeMirror;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        this.typeMirror = typeMirror;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}

package com.delta.api;

import android.app.Activity;

import com.delta.annotationmodule.ViewBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/5/15 15:37
 */


public class Butterknife {

    public static Map<Activity, ViewBinder> map = new HashMap<>();

    public static void bind(Activity activity) {
        String className = activity.getClass().getName();
        try {
            ViewBinder viewBinder = map.get(className);
            if (viewBinder != null) {
                throw new Exception("绑定重复");
            }
            Class<?> aClass = Class.forName(className + "$$ViewBinder");
            viewBinder = (ViewBinder) aClass.newInstance();
            viewBinder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


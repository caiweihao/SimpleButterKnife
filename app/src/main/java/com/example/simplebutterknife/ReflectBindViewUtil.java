package com.example.simplebutterknife;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectBindViewUtil {
    public static void bind(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReflectBindView.class)) {
                ReflectBindView annotation = field.getAnnotation(ReflectBindView.class);
                int resId = annotation.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object invoke = method.invoke(activity, resId);
                    field.setAccessible(true);
                    field.set(activity, invoke);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void unbind(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReflectBindView.class)) {
                try {
                    field.setAccessible(true);
                    field.set(activity, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showMyBindField(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReflectBindView.class)) {
                field.setAccessible(true);
                try {
                    System.out.println("field name is " + field.getName() + " value is " + field.get(activity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

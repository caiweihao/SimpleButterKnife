SimpleButterKnife
========

`SimpleButterKnife` 用两种方式来实现findViewById功能。
1. 使用反射（运行时获取）
2. Annotation Processor

上面两种方式都会使用注解，先来简单介绍一下注解。
```java
package com.example.simplebutterknife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //注解保留范围，SOURCE代表代码级别（Annotation Processor可以使用），CLASS代表编译器级别 ， RUNTIME代码VM级别（反射可以使用）
@Target(ElementType.FIELD) //注解使用范围
public @interface ReflectBindView {
    int value();
}
```
   
因为运行时注解须要在Activity初始化中进行绑定操做，调用了大量反射相关代码，在界面复杂的状况下，使用这种方法就会严重影响Activity初始化效率。而ButterKnife使用了更高效的方式——Annotation Processor来完成这一工做，那么什么是 Annotation Processor呢？
### 反射相关功能

```java
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

    public static void showMyBindField(Activity activity, String prefix) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReflectBindView.class)) {
                field.setAccessible(true);
                try {
                    System.out.println(prefix + " field name is " + field.getName() + " value is " + field.get(activity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

```
Annotation Processor使用 [javapoet](https://github.com/square/javapoet)生成Java文件。
Android整个编译过程就是 source(源代码) -> processor（处理器） -> generate （文件生成）-> javacompiler -> .class 文件 -> .dex(只针对安卓)。
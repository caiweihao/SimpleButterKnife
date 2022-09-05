package com.example.simplebutterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProcessBindViewUtil {
    public static void bind(Activity activity) {
        try {
            Class bindClass = Class.forName(activity.getClass().getCanonicalName() + "$Binding");
            Class activityClass = activity.getClass();
            Constructor constructor = bindClass.getConstructor(activityClass);
            constructor.newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}

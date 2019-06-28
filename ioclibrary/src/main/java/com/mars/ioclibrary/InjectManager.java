package com.mars.ioclibrary;

import android.app.Activity;
import android.view.View;

import com.mars.ioclibrary.annotation.ContentView;
import com.mars.ioclibrary.annotation.EventBase;
import com.mars.ioclibrary.annotation.InjectView;
import com.mars.ioclibrary.listener.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 注入管理类
public class InjectManager {

    public static void inject(Activity activity) {
        // 布局的注入
        injectLayout(activity);
        // 控件的注入
        injectViews(activity);
        // 事件的注入
        injectEvents(activity);
    }

    // 布局的注入
    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类之上的注释
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取这个注解的值
            int layoutId = contentView.value();
            // 第一种方式：
            // activity.setContentView(layoutId);
            // 第二种方式：
            try {
                // 获取setContentView(R.layout.activity_main)方法
                Method setContentView = clazz.getMethod("setContentView", int.class);
                // 执行setContentView(R.layout.activity_main)方法
                setContentView.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 控件的注入
    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 循环每个属性
        for (Field field : fields) {
            // 获取属性的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                // 获取这个注解的值
                int viewId = injectView.value();
                // 第一种方法：
                // View view = activity.findViewById(viewId);

                //第二种方法：
                try {
                    // 获取findViewById(R.id.btn)方法
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    // 执行findViewById(R.id.btn)方法,但是没有赋值
                    Object view = findViewById.invoke(activity, viewId);
                    // *****设置访问权限，private *********
                    field.setAccessible(true);
                    // 属性的值付给控件，在当前的MainActivity
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 事件的注入
    public static void injectEvents(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历方法
        for (Method method : methods) {
            // 获取每个方法的注解（多个控件id）
            Annotation[] annotations = method.getAnnotations();
            // 遍历注解
            for (Annotation annotation : annotations) {
                // 获取注解上的注解
                // 获取OnClick注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    // 通过EventBase指定获取
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) { // 有些方法没有EventBase注解
                        // 事件3大成员
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callBackListener = eventBase.callBackListener();

                        // 获取注解的值，执行方法再去获得注解的值
                        try {
                            // 通过annotationType获取onClick注解的value值
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 执行value方法获得注解的值
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            // 代理方式（3个成员组合）
                            // 拦截方法
                            // 得到监听的代理对象（新建代理单例、类的加载器，指定要代理的对象类的类型、class实例）
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            // 添加到拦截列表里面
                            handler.addMethodMap(callBackListener, method);
                            // 监听对象的代理对象
                            // ClassLoader loader:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                            // Class<?>[] interfaces:目标对象实现的接口的类型,使用泛型方式确认类型
                            // InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                    new Class[]{listenerType}, handler);

                            // 遍历注解的值
                            for (int viewId : viewIds) {
                                // 获得当前activity的view（赋值）
                                View view = activity.findViewById(viewId);
                                // 获取指定的方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                // 执行方法
                                setter.invoke(view, listener);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

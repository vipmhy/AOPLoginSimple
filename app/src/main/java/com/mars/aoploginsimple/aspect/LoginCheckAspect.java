package com.mars.aoploginsimple.aspect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.mars.aoploginsimple.LoginActivity;
import com.mars.aoploginsimple.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect // 定义切面类
public class LoginCheckAspect {
    private final static String TAG = "mars >>>";

    // 1、应用中用到了哪些注解，放到当前的切入点进行处理（找到需要处理的切入点）
    // execution，以方法执行时作为切点，触发Aspect类
    // * *(..)) 可以处理ClickBehavior这个类所有的方法
    @Pointcut("execution(@com.mars.aoploginsimple.annotation.LoginCheck * *(..))")
    public void methodPointCut() {}

    // 2、对切入点如何处理
    @Around("methodPointCut()")
    public Object jointPotin(ProceedingJoinPoint joinPoint) throws Throwable {
        Context context = (Context) joinPoint.getThis();
        boolean isLogin = true;
        if (isLogin){
            Log.e(TAG,"检测到已登录！");
            return joinPoint.proceed();// MainActivity中切面的方法(不拦截，直接执行注解方法中逻辑)
        } else{
            Log.e(TAG,"检测到未登录！");
            Toast.makeText(context,"请先登录！", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return null;
        }
    }
}

package com.mars.ioclibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 作用在属性之上
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {

    int value();
}

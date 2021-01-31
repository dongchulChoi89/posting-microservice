package com.choi.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // set where the position of the annotation is // PARAMETER means only instance of the method's parameter can use this annotation
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // set this class as annotation class, and it means an annotation has built
}

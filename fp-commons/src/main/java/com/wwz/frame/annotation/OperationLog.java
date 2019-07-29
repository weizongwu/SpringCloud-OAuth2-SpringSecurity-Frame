package com.wwz.frame.annotation;

import java.lang.annotation.*;

/**
 * @Description 操作日志
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    String description() default "";
}

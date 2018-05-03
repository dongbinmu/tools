package com.dongbin.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dongbin on 2018/4/27.
 */
public class ReflectionUtil {

    private static final Logger logger= LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object invokeMethod(Object obj, Method method, Object... agrs){
        Object result = null;
        method.setAccessible(true);
        try {
            result=method.invoke(obj,agrs);
        } catch (IllegalAccessException e) {
            logger.error("执行方法异常",e);
        } catch (InvocationTargetException e) {
            logger.error("执行方法异常",e);
        }
        return result;
    }
    public static void setFiled(Object obj, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            logger.error("设置对象属性失败：",e);
            throw  new RuntimeException(e);
        }

    }
}

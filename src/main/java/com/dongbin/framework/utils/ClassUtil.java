package com.dongbin.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dongbin on 2018/4/27.
 */
public final class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }




}

package com.dongbin.framework.beans.factory;

import com.dongbin.framework.annotation.Bean;
import com.dongbin.framework.annotation.Component;
import com.dongbin.framework.annotation.Resource;
import com.dongbin.framework.annotation.Service;
import com.dongbin.framework.utils.ClassUtil;
import com.dongbin.framework.utils.PropertiesUtil;
import com.dongbin.framework.utils.ReflectionUtil;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean的工厂类
 */
public class BeanFactory {

    //存放bean应用的map
    private static final Map<Class<?>, Object> BEAN_DEFINITION_MAP = new ConcurrentHashMap<>();

    static {
        Properties properties = PropertiesUtil.loadProps("tools.properties");
        String packageName = properties.getProperty("tools.package.scan", "");
        initBean(packageName);
    }

    /**
     * 获取bean 直接获取
     *
     * @param clazz
     * @return
     * @throws Exception
     */

    public <T> T getBean(Class<T> clazz) throws Exception {
        Object o = BEAN_DEFINITION_MAP.get(clazz);
        if (o == null) {
            throw new IllegalArgumentException("this bean is not defined," + clazz);
        }
        return (T) o;
    }

    private static void initBean(String packageName) {
        try {
            Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replaceAll("\\.", "/"));

            while (urls.hasMoreElements()) {
                addBeans(urls.nextElement().getPath(), packageName);
            }

            inject();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private static void addBeans(String filePath, String packageName) {
        File[] files = getClassFile(filePath);
        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (f.isFile()) {
                    try {
                        Class<?> clazz = Class.forName(packageName + "." + fileName.substring(0, fileName.lastIndexOf(".")));
                        if (clazz.isAnnotationPresent(Bean.class) || clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Service.class)) {
                            BEAN_DEFINITION_MAP.put(clazz, clazz.newInstance());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    addBeans(f.getPath(), packageName + "." + fileName);
                }
            }
        }
    }

    private static File[] getClassFile(String filePath) {
        return new File(filePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }

    /**
     * 注入属性bean
     */
    private static void inject() throws IllegalAccessException {
        for (Map.Entry<Class<?>, Object> entry : BEAN_DEFINITION_MAP.entrySet()) {
            Class<?> clazz = entry.getKey();
            Object bean = entry.getValue();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Class<?> fieldClazz = field.getType();
                if (field.isAnnotationPresent(Resource.class)) {
                    ReflectionUtil.setFiled(bean, field, BEAN_DEFINITION_MAP.get(fieldClazz));
                }
            }
        }
    }
}

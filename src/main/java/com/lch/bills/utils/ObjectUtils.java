package com.lch.bills.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 *
 * @date 2018年10月30日 上午10:15:38
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    /**
     * 转换为Double类型
     */
    public static Double toDouble(final Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return NumberUtils.toDouble(StringUtils.trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(final Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(final Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Long类型(转换失败或为空时默认返回null)
     */
    public static Long toLongForNull(final Object val) {
        try {
            return Long.valueOf(StringUtils.trim(val.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(final Object val) {
        return toLong(val).intValue();
    }

    /**
     * 转换为Boolean类型 'true', 'on', 'y', 't', 'yes' or '1' (case insensitive) will return true. Otherwise, false is returned.
     */
    public static Boolean toBoolean(final Object val) {
        if (val == null) {
            return false;
        }
        return BooleanUtils.toBoolean(val.toString()) || "1".equals(val.toString());
    }


    /**
     * 转换为字符串
     *
     * @param obj
     * @return
     */
    public static String toString(final Object obj) {
        return toString(obj, StringUtils.EMPTY);
    }

    /**
     * 如果对象为空，则使用defaultVal值
     *
     * @param obj
     * @param defaultVal
     * @return
     */
    public static String toString(final Object obj, final String defaultVal) {
        return obj == null ? defaultVal : obj.toString();
    }


    /**
     * 拷贝一个对象（但是子对象无法拷贝）
     *
     * @param source
     * @param ignoreProperties
     */
    public static Object copyBean(Object source, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        Object target = null;
        try {
            target = source.getClass().newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 注解到对象复制，只复制能匹配上的方法。 硕正组件用。
     *
     * @param annotation
     * @param object
     */
    public static void annotationToObject(Object annotation, Object object) {
        if (annotation != null && object != null) {
            Class<?> annotationClass = annotation.getClass();
            Class<?> objectClass = object.getClass();
            for (Method m : objectClass.getMethods()) {
                if (StringUtils.startsWith(m.getName(), "set")) {
                    try {
                        String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
                        Object obj = annotationClass.getMethod(s).invoke(annotation);
                        if (obj != null && !"".equals(obj.toString())) {
//							if (object == null){
//								object = objectClass.newInstance();
//							}
                            m.invoke(object, obj);
                        }
                    } catch (Exception e) {
                        // 忽略所有设置失败方法
                    }
                }
            }
        }
    }

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Object object = null;
        if (bytes.length > 0) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bais);) {
                object = ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 判断某个对象是否在所给对象集合中
     *
     * @param obj
     * @param objs
     * @return
     */
    public static boolean isInclude(Object obj, Object... objs) {
        return ArrayUtils.contains(objs, obj);
    }

}

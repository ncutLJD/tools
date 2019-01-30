package utils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class BeanUtil {

    public static <T> T toBean(Object source, Class<T> targetClass) {
        try {
            if(source==null){
                return null;
            }

            T targetObject = targetClass.newInstance();
            BeanUtils.copyProperties(source, targetObject);
            return targetObject;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(Collection sourcelist, Class<T> targetClass) {
        List<T> list = new ArrayList<T>();
        if (CollectionUtils.isNotEmpty(sourcelist)) {
            for (Object source : sourcelist) {
                T target = toBean(source,targetClass);
                list.add(target);
            }
        }
        return list;
    }
    public static <T> Set<T> toSet(Collection sourcelist, Class<T> targetClass) {
        Set<T> set = new HashSet<T>();
        if (CollectionUtils.isNotEmpty(sourcelist)) {
            for (Object source : sourcelist) {
                T target = toBean(source,targetClass);
                set.add(target);
            }
        }
        return set;
    }

    /**
     * 对象之间拷贝
     *
     * @param source原始
     * @param target目标
     * @param ignoreProperties
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 对象之间拷贝
     *
     * @param source 原始
     * @param target 目标
     * @param ignoreProperties 忽略字段
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 对象之间拷贝（过滤null值）
     *
     * @param source 原始
     * @param target 目标
     */
    public static void copyPropertiesNotNull(Object source, Object target) {
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if(value ==null){
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 对象List之间拷贝
     *
     * @param source 原始
     * @param target 目标
     * @param targetClass 目标类
     */
    public static void copyListProperties(Object sourcelist, Object targetList, Class targetClass) {
        for (Object source : (List) sourcelist) {
            Object target = null;
            try {
                target = targetClass.newInstance();
                BeanUtils.copyProperties(source, target);
                ((List) targetList).add(target);
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * Discription:[拷贝一个集合的数据到另一个集合]
     * </p>
     *
     * @param sourcelist
     * @param targetList
     * @param targetClass
     */
    public static void copyCollectionProperties(Collection sourcelist, Collection targetList, Class targetClass) {
        for (Object source : sourcelist) {
            Object target = null;
            try {
                target = targetClass.newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对象List之间拷贝
     *
     * @param source
     * @param target
     * @param targetClass 目标类
     * @param ignoreProperties 忽略字段
     */
    public static void copyListProperties(Object sourcelist, Object targetList, Class targetClass,
                                          String... ignoreProperties) {
        for (Object source : (List) sourcelist) {
            Object target = null;
            try {
                target = targetClass.newInstance();
                BeanUtils.copyProperties(source, target, ignoreProperties);
                ((List) targetList).add(target);
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public static <F,T> List<F> getFeildList(Collection<T> sourcelist,String feildName,Class<F> targetClass){
        if(sourcelist==null||sourcelist.isEmpty())
            return null;
        List<F> list = new ArrayList<F>();
        Field field = null;
        for(T t:sourcelist){
            try {
                if(field==null){
                    field = t.getClass().getDeclaredField(feildName);
                }
                field.setAccessible(true);
                F value = (F)field.get(t);
                list.add(value);
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(list.isEmpty()){
            list=null;
        }
        return list;
    }
}

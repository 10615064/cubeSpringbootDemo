package com.example.cubespringbootdemo.utils;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
public class JsonUtils {
    /**
     * List映射List
     */
    public static <T,V> List<V> listTolist(List<T> entityList,Class<V> voClass){
        List<V> voList = new ArrayList<>();
        for(T entity:entityList){
            voList.add(objectToObject(entity,voClass));
        }
        return voList;
    }

    /**
     * Object映射Object
     */
    public static <T,V> V objectToObject(T entity,Class<V> voClass){
        try{
            V voInstance = voClass.getDeclaredConstructor().newInstance();
            Field[] fields = entity.getClass().getDeclaredFields();
            for(Field field:fields){
                try{
                    field.setAccessible(true);
                    Field voField = voClass.getDeclaredField(field.getName());
                    voField.setAccessible(true);
                    Object value = field.get(entity);
                    if(value!=null){
                        voField.set(voInstance,value);
                    }
                }catch (NoSuchFieldException e){

                }
            }
            return voInstance;
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return null;
        }
    }
}

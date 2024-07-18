package com.crm.proxy;

import java.lang.reflect.InvocationTargetException;

import javassist.util.proxy.ProxyFactory;

public class VersioningProxyFactoryUtil{

    public static Object createProxy(String className) throws Exception{

        Object target;
        Object proxy;
        try{
            Class<?> clazz = Class.forName(className);
            target = clazz.getDeclaredConstructor().newInstance();

            ProxyFactory factory = new ProxyFactory();
            factory.setSuperclass(target.getClass());
            Class<?> proxyClass = factory.createClass();
            proxy = proxyClass.getDeclaredConstructor().newInstance();
            return proxy;
        } catch(InvocationTargetException e){
            throw new Exception("Failed to instantiate proxy", e);
        } catch(Exception e){
            throw new RuntimeException("Failed to instantiate proxy", e);
        }

    }
}

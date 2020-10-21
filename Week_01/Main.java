package com.test;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        try{
            MyClassLoader mcl = new MyClassLoader();
            Class<?> clazz = Class.forName("Hello", true, mcl);
            Object obj = clazz.newInstance();
            Method hello=clazz.getDeclaredMethod("hello");
            hello.invoke(obj,null);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

package com.example.demo3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author TRITRONIK-PC_10040
 * @since 30/03/2023
 */

@SpringBootTest
@Slf4j
public class CalculatorTest {

    @Autowired
    private CustomClassLoader customClassLoader;


    @Test
    public void testCall() throws ClassNotFoundException {
        JarClassLoader jcl = new JarClassLoader();

        //Loading classes from different sources
        jcl.add("demo2.jar");

        JclObjectFactory factory = JclObjectFactory.getInstance();

        //Create object of loaded class
        Object obj = factory.create(jcl, "com.example.demo2.Calculator");

        Class<?> aClass = obj.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .forEach(System.out::println);


    }

    @Test
    public void testInvokeMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        JarClassLoader jcl = new JarClassLoader();
        //Loading classes from different sources
        jcl.add("demo2.jar");

        JclObjectFactory factory = JclObjectFactory.getInstance();

        //Create object of loaded class
        Object obj = factory.create(jcl, "com.example.demo2.Calculator");

        Method plus = obj.getClass().getMethod("plus", int.class, int.class);


    }
}

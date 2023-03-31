package com.example.demo3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author kurakuraninja
 * @since 30/03/23
 */

@SpringBootTest
@Slf4j
public class MemberServiceTest {


    @Autowired
    private CustomClassLoader customClassLoader;

    @Test
    public void testSave() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        //customClassLoader.loadJar();
        JarClassLoader jcl = new JarClassLoader();

        //Loading classes from different sources
        jcl.add("demo23.jar");

        JclObjectFactory factory = JclObjectFactory.getInstance();

        //Create object of loaded class
        Object objService = factory.create(jcl, "com.example.demo2.services.MemberService");
        Class<?> memberService = objService.getClass();

        Object objEntities = factory.create(jcl, "com.example.demo2.entities.Member");
        Class<?> memberEntities = objEntities.getClass();

        final Object memberObject = memberEntities.getDeclaredConstructor().newInstance();
        final Field fName = memberEntities.getDeclaredField("name");
        final Field fId = memberEntities.getDeclaredField("id");
        fName.setAccessible(true);
        fName.set(memberObject,"Harun");
        fId.setAccessible(true);
        fId.set(memberObject,"12121212");


        Method[] declaredMethods = memberService.getDeclaredMethods();
        for (Method method: declaredMethods){
            System.out.println(method.getName());
        }


        Method save = memberService.getMethod("save");
        final Object result = save.invoke(memberService.getDeclaredConstructor().newInstance());
        System.out.println(result);



//        Class<?> aClass = obj.getClass();
//
//        final Method plus = aClass.getMethod("plus", int.class, int.class);
//        final Object invoke = plus.invoke(aClass.getDeclaredConstructor().newInstance(),2,2);
//        log.info("Result : {}", invoke);

    }
}

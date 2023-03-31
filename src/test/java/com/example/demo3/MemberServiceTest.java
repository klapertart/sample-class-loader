package com.example.demo3;

import com.example.demo3.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kurakuraninja
 * @since 30/03/23
 */

@SpringBootTest
@Slf4j
public class MemberServiceTest {


    @Autowired
    private CustomClassLoader customClassLoader;

    @Autowired
    private SpringUtil springUtil;

    @Autowired
    private ApplicationContext context;

    @Test
    public void testSave() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        customClassLoader.loadJar();
        JarClassLoader jcl = new JarClassLoader();

        //Loading classes from different sources
        jcl.add("demo2.jar");

        JclObjectFactory factory = JclObjectFactory.getInstance();

        //Create object of loaded class
        Object objService = factory.create(jcl, "com.example.demo2.services.MemberService");
        Class<?> memberService = objService.getClass();

        Object objEntities = factory.create(jcl, "com.example.demo2.entities.Member");
        Class<?> memberEntities = objEntities.getClass();

        final Object memberObject = memberEntities.getDeclaredConstructor().newInstance();
        final Field fName = memberEntities.getDeclaredField("name");
        fName.setAccessible(true);
        fName.set(memberObject,"Harun");



        Method save = memberService.getMethod("save");
        final Object result = save.invoke(memberService.getDeclaredConstructor().newInstance(),memberObject);
        System.out.println(result);
    }

    @Test
    public void testScanClass(){
        //customClassLoader.loadJar();
        JarClassLoader jcl = new JarClassLoader();

        //Loading classes from different sources
        jcl.add("demo23.jar");

        Map<String, byte[]> loadedResourceMap = jcl.getLoadedResources();


        Set<String> loadedSet= loadedResourceMap.keySet().stream()
                .filter(s -> s.startsWith("com/example/demo2/")).collect(Collectors.toSet());

        //System.out.println(loadedSet.size());

        for (String localSet : loadedSet) {

            String modifiedString = localSet.replace("/", ".").replace(".class", "");
            log.info("modified string " + modifiedString);

        }
    }

    @Test
    public void testMemberClass(){
        JarClassLoader jcl = new JarClassLoader();

        jcl.add("demo23.jar");
        JclObjectFactory factory = JclObjectFactory.getInstance();

        //Create object of loaded class
        Class<?> member = factory.create(jcl, "com.example.demo2.entities.Member").getClass();

        springUtil.registerBean(member.getName(),member);
        Object bean = springUtil.getBean(member.getName());


        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

//        Class<?> aClass = obj.getClass();
//
//        final Method plus = aClass.getMethod("plus", int.class, int.class);
//        final Object invoke = plus.invoke(aClass.getDeclaredConstructor().newInstance(),2,2);
//        log.info("Result : {}", invoke);

    }

    @Test
    public void testCustomClassLoader() throws ClassNotFoundException {
        customClassLoader.loadJar();
    }
}

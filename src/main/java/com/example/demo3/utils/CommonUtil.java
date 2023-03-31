package com.example.demo3.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.xeustechnologies.jcl.JarClassLoader;

import java.util.Arrays;
import java.util.Set;

/**
 * @author kurakuraninja
 * @since 01/04/23
 */

@Component
@Slf4j
public class CommonUtil {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SpringUtil springUtil;

    public void displayAllBeans(){
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @SneakyThrows
    public void registerAllClasses(JarClassLoader jcl, Set<String> classes) throws ClassNotFoundException {
        for (String aClass : classes) {

            String modifiedString = aClass.replace("/", ".").replace(".class", "");
            log.info("modified string " + modifiedString);

            final Class<?> loadedClass = jcl.loadClass(modifiedString);

            // register bean to context
            springUtil.registerBean(loadedClass.getName(),loadedClass);
        }

    }
}

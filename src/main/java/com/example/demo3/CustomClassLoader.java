package com.example.demo3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.xeustechnologies.jcl.JarClassLoader;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author TRITRONIK-PC_10040
 * @since 30/03/2023
 */

@Component
public class CustomClassLoader {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigurableApplicationContext applicationContext;

    public void loadJar() throws ClassNotFoundException {
        JarClassLoader jcl = new JarClassLoader();

        jcl.add("/Users/kurakuraninja/Downloads/tesjar/");  //loaded all the jars from test folder

        Map<String, byte[]> loadedResourceMap = jcl.getLoadedResources();


        Set<String> loadedSet= loadedResourceMap.keySet().stream()
                .filter(s -> s.startsWith("com/example/demo2/")).collect(Collectors.toSet());

        //System.out.println(loadedSet.size());

        for (String localSet : loadedSet) {

            String modifiedString = localSet.replace("/", ".").replace(".class", "");
            //logger.info("modified string " + modifiedString);

            final Class<?> loadedClass = jcl.loadClass(modifiedString);

            try {
                Object loadedObject =  applicationContext.getAutowireCapableBeanFactory()
                        .createBean(loadedClass); //autowiring the loaded classes

            } catch (Exception e) {
                logger.info("Exception occured while loading " + modifiedString
                        + " exception is" + e.getMessage());
            }
        }

    }

}
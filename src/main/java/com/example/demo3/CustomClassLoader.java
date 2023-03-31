package com.example.demo3;

import com.example.demo3.utils.CommonUtil;
import com.example.demo3.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.xeustechnologies.jcl.JarClassLoader;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kurakuraninja
 * @since 30/03/2023
 */

@Component
public class CustomClassLoader {

    @Autowired
    private CommonUtil commonUtil;

    @Value("${jar.path}")
    private String jarPath;

    @Value("${package.name}")
    private String packageName;

    public void loadJar() throws ClassNotFoundException {
        JarClassLoader jcl = new JarClassLoader();

        //loaded all the jars from a directory
        jcl.add(jarPath);

        Map<String, byte[]> loadedResourceMap = jcl.getLoadedResources();

        Set<String> loadedSet= loadedResourceMap.keySet().stream()
                .filter(s -> s.startsWith(packageName)).collect(Collectors.toSet());

        commonUtil.registerAllClasses(jcl,loadedSet);

        commonUtil.displayAllBeans();
    }

}
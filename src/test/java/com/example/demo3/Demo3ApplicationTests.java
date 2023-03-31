package com.example.demo3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootTest
class Demo3ApplicationTests {
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private CustomClassLoader customClassLoader;

	@Test
	void contextLoads() {
	}

	@Test
	void showAllBean() throws ClassNotFoundException {
		customClassLoader.loadJar();
	}

}

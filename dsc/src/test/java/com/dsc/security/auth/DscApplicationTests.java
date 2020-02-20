package com.dsc.security.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DscApplicationTests {

	@Test
	void contextLoads() {
		assertNull(DscApplicationTests.doSomething());  // JUnit assertion
		  assertThat(DscApplicationTests.doSomething()).isNull();  // Fest assertion

		
	}

	private static Object doSomething() {
		// TODO Auto-generated method stub
		return null;
	}

}

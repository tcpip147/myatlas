package com.look4counter.myatlas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyAtlasLanguageDriverTest {

	private MyAtlasLanguageDriver driver;

	@BeforeEach
	void setUp() {
		driver = new MyAtlasLanguageDriver();
	}

	@Test
	public void testTransform() {
		String rawSql = """
				/* select user */
				SELECT *
				  FROM user
				 WHERE id = 'atlas'
				   /*@ <if test="age != null"> */
				   AND age = :age
				   /*@ </if> */
				""";
		System.out.println(driver.transform(rawSql));
	}
}

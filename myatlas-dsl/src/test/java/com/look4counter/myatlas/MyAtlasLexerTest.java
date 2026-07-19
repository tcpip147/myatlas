package com.look4counter.myatlas;

import org.junit.jupiter.api.Test;

public class MyAtlasLexerTest {

	@Test
	public void testTokenize() {
		String script = """
				/* select user */
				SELECT *
				  FROM user
				 WHERE id = 'atlas'
				   /*@ <if test="age != null"> */
				   AND age = :age
				   /*@ </if> */
				""";
		MyAtlasLexer lexer = new MyAtlasLexer(script);
		while (lexer.hasNext()) {
			System.out.println(lexer.next());
		}
	}
}

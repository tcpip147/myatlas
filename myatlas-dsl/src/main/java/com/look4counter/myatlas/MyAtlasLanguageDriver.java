package com.look4counter.myatlas;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

public class MyAtlasLanguageDriver extends XMLLanguageDriver {

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		return super.createSqlSource(configuration, transform(script), parameterType);
	}

	String transform(String script) {
		MyAtlasLexer lexer = new MyAtlasLexer(script);

		StringBuilder sb = new StringBuilder();

		while (lexer.hasNext()) {
			MyAtlasToken token = lexer.next();
			if (token.getType() == MyAtlasTokenType.PARAMETER) {
				sb.append("#{" + token.getValue().substring(1) + "}");
			} else if (token.getType() == MyAtlasTokenType.DYNAMIC_SQL) {
				sb.append(token.getValue().subSequence(3, token.getValue().length() - 3).toString().trim());
			} else {
				sb.append(token.getValue());
			}
		}

		return sb.toString();
	}
}

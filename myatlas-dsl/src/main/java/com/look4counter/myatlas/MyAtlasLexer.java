package com.look4counter.myatlas;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyAtlasLexer implements Iterator<MyAtlasToken> {

	private final char[] chars;
	private final int charsLen;
	private int cursor = 0;

	public MyAtlasLexer(String script) {
		this.chars = script.toCharArray();
		this.charsLen = script.length();
	}

	@Override
	public boolean hasNext() {
		return cursor <= charsLen;
	}

	@Override
	public MyAtlasToken next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more token");
		}

		if (cursor == charsLen) {
			cursor++;
			return new MyAtlasToken(MyAtlasTokenType.EOF, "", cursor, 0);
		}

		int startOffset = cursor;

		if (Character.isWhitespace(chars[cursor])) {
			cursor++;

			while (cursor < charsLen && Character.isWhitespace(chars[cursor])) {
				cursor++;
			}

			String val = new String(chars, startOffset, cursor - startOffset);

			return new MyAtlasToken(MyAtlasTokenType.SPACE, val, startOffset, cursor - startOffset);
		}

		if (match(cursor, new char[] { '/', '*' })) {
			cursor += 2;

			boolean isDynamic = false;
			if (cursor < charsLen && chars[cursor] == '@') {
				isDynamic = true;
				cursor++;
			}

			while (cursor < charsLen && !match(cursor, new char[] { '*', '/' })) {
				cursor++;
			}

			if (cursor < charsLen) {
				cursor += 2;
			}

			String val = new String(chars, startOffset, cursor - startOffset);

			MyAtlasTokenType type = isDynamic ? MyAtlasTokenType.DYNAMIC_SQL : MyAtlasTokenType.MULTI_LINE_COMMENT;

			return new MyAtlasToken(type, val, startOffset, cursor - startOffset);
		}

		if (chars[cursor] == '"') {
			cursor++;
			while (cursor < charsLen && chars[cursor] != '"') {
				if (chars[cursor] == '\\' && cursor + 1 < charsLen && chars[cursor + 1] == '"') {
					cursor += 2;
				} else {
					cursor++;
				}
			}
			if (cursor < charsLen) {
				cursor++;
			}
			String val = new String(chars, startOffset, cursor - startOffset);
			return new MyAtlasToken(MyAtlasTokenType.DOUBLE_QUOTE, val, startOffset, cursor - startOffset);
		}

		if (chars[cursor] == '\'') {
			cursor++;
			while (cursor < charsLen && chars[cursor] != '\'') {
				if (chars[cursor] == '\\' && cursor + 1 < charsLen && chars[cursor + 1] == '\'') {
					cursor += 2;
				} else {
					cursor++;
				}
			}
			if (cursor < charsLen) {
				cursor++;
			}
			String val = new String(chars, startOffset, cursor - startOffset);
			return new MyAtlasToken(MyAtlasTokenType.SINGLE_QUOTE, val, startOffset, cursor - startOffset);
		}

		if (chars[cursor] == ':' && cursor + 1 < charsLen && chars[cursor + 1] != ':') {
			cursor++;

			if (cursor < charsLen && !Character.isWhitespace(chars[cursor])) {
				while (cursor < charsLen && !Character.isWhitespace(chars[cursor])) {
					cursor++;
				}
				String val = new String(chars, startOffset, cursor - startOffset);
				return new MyAtlasToken(MyAtlasTokenType.PARAMETER, val, startOffset, cursor - startOffset);
			}
		}

		if (!Character.isWhitespace(chars[cursor])) {
			while (cursor < charsLen && !Character.isWhitespace(chars[cursor])) {
				cursor++;
			}
			String val = new String(chars, startOffset, cursor - startOffset);
			if (isKeyword(val)) {
				return new MyAtlasToken(MyAtlasTokenType.KEYWORD, val, startOffset, cursor - startOffset);
			}
			return new MyAtlasToken(MyAtlasTokenType.IDENTIFIER, val, startOffset, cursor - startOffset);
		}

		cursor++;
		String val = new String(chars, startOffset, cursor - startOffset);
		return new MyAtlasToken(MyAtlasTokenType.UNKNOWN, val, startOffset, cursor - startOffset);
	}

	private boolean match(int pos, char[] target) {
		int targetLen = target.length;
		if (pos + targetLen > charsLen) {
			return false;
		}
		for (int i = 0; i < targetLen; i++) {
			if (chars[pos + i] != target[i]) {
				return false;
			}
		}
		return true;
	}

	private boolean isKeyword(String val) {
		String upper = val.toUpperCase();
		return "SELECT".equals(upper) || "INSERT".equals(upper) || "UPDATE".equals(upper) || "DELETE".equals(upper)
				|| "WHERE".equals(upper) || "FROM".equals(upper);
	}

}

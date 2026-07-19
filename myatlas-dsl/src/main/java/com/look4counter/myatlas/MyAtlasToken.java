package com.look4counter.myatlas;

public class MyAtlasToken {

	private MyAtlasTokenType type;
	private String value;
	private int offset;
	private int length;

	public MyAtlasToken(MyAtlasTokenType type, String value, int offset, int length) {
		super();
		this.type = type;
		this.value = value;
		this.offset = offset;
		this.length = length;
	}

	public MyAtlasTokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "MyAtlasToken [type=" + type + ", value=" + value + ", offset=" + offset + ", length=" + length + "]";
	}

}

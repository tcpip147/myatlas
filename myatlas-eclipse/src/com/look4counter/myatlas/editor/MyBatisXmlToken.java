package com.look4counter.myatlas.editor;

public class MyBatisXmlToken {

	private String type;
	private int offset;
	private int length;

	public MyBatisXmlToken(String type, int offset, int length) {
		this.type = type;
		this.offset = offset;
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "MyBatisXmlToken [type=" + type + ", offset=" + offset + ", length=" + length + "]";
	}
}

package com.look4counter.myatlas.model;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MapperFile {

	private List<MapperElement> elements = new ArrayList<>();

	public MapperFile(InputStream is) {
		SAXParser saxParser;
		try {
			saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(new InputSource(is), new XmlHandler());
		} catch (Exception e) {
		}
	}

	public MapperFile(String xml) {
		SAXParser saxParser;
		try {
			saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(new InputSource(new StringReader(xml)), new XmlHandler());
		} catch (Exception e) {
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	private class XmlHandler extends DefaultHandler {

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
		}
	}
}
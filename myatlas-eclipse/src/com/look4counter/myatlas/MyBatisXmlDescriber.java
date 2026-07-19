package com.look4counter.myatlas;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.internal.content.LazyInputStream;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

public class MyBatisXmlDescriber implements ITextContentDescriber {

	private static final Set<String> SUPPORTED_ROOTS = Set.of("mapper");

	@Override
	public int describe(InputStream contents, IContentDescription description) throws IOException {
		return describe(contents);
	}

	@Override
	public int describe(Reader contents, IContentDescription description) throws IOException {
		return describe(contents);
	}

	private int describe(Object contents) {
		XMLStreamReader reader = null;
		try {
			reader = createStreamReader(contents);
			while (reader.hasNext()) {
				int event = reader.next();
				if (event == XMLStreamConstants.START_ELEMENT) {
					String root = reader.getLocalName();
					return SUPPORTED_ROOTS.contains(root) ? VALID : INVALID;
				}
			}
			return INVALID;
		} catch (XMLStreamException e) {
			return INVALID;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (XMLStreamException e) {
				}
			}
		}
	}

	private XMLStreamReader createStreamReader(Object contents) throws XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		if (contents instanceof InputStream is) {
			return factory.createXMLStreamReader(is);
		} else if (contents instanceof Reader reader) {
			return factory.createXMLStreamReader(reader);
		}
		throw new IllegalArgumentException("Unsupported content type");
	}

	@Override
	public QualifiedName[] getSupportedOptions() {
		return new QualifiedName[0];
	}

}
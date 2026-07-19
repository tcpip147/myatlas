package com.look4counter.myatlas.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;

import com.look4counter.myatlas.MyAtlasLexer;
import com.look4counter.myatlas.MyAtlasToken;
import com.look4counter.myatlas.MyAtlasTokenType;

public class MyBatisXmlRepairer implements IPresentationRepairer, IPresentationDamager {

	private static final String BLOCK_TEXT_COLOR = "BLOCK_TEXT_COLOR";
	private static final String UNDEFINED_COLOR = "UNDEFINED_COLOR";
	private static final String WHITE_SPACE_COLOR = "WHITE_SPACE_COLOR";
	private static final String XML_ATTLIST_DECL_CLOSE_COLOR = "XML_ATTLIST_DECL_CLOSE_COLOR";
	private static final String XML_ATTLIST_DECL_CONTENT_COLOR = "XML_ATTLIST_DECL_CONTENT_COLOR";
	private static final String XML_ATTLIST_DECL_NAME_COLOR = "XML_ATTLIST_DECL_NAME_COLOR";
	private static final String XML_ATTLIST_DECLARATION_COLOR = "XML_ATTLIST_DECLARATION_COLOR";
	private static final String XML_CDATA_CLOSE_COLOR = "XML_CDATA_CLOSE_COLOR";
	private static final String XML_CDATA_OPEN_COLOR = "XML_CDATA_OPEN_COLOR";
	private static final String XML_CDATA_TEXT_COLOR = "XML_CDATA_TEXT_COLOR";
	private static final String XML_CHAR_REFERENCE_COLOR = "XML_CHAR_REFERENCE_COLOR";
	private static final String XML_COMMENT_CLOSE_COLOR = "XML_COMMENT_CLOSE_COLOR";
	private static final String XML_COMMENT_OPEN_COLOR = "XML_COMMENT_OPEN_COLOR";
	private static final String XML_COMMENT_TEXT_COLOR = "XML_COMMENT_TEXT_COLOR";
	private static final String XML_CONTENT_COLOR = "XML_CONTENT_COLOR";
	private static final String XML_DECLARATION_CLOSE_COLOR = "XML_DECLARATION_CLOSE_COLOR";
	private static final String XML_DECLARATION_OPEN_COLOR = "XML_DECLARATION_OPEN_COLOR";
	private static final String XML_DOCTYPE_DECLARATION_COLOR = "XML_DOCTYPE_DECLARATION_COLOR";
	private static final String XML_DOCTYPE_DECLARATION_CLOSE_COLOR = "XML_DOCTYPE_DECLARATION_CLOSE_COLOR";
	private static final String XML_DOCTYPE_EXTERNAL_ID_PUBLIC_COLOR = "XML_DOCTYPE_EXTERNAL_ID_PUBLIC_COLOR";
	private static final String XML_DOCTYPE_EXTERNAL_ID_PUBREF_COLOR = "XML_DOCTYPE_EXTERNAL_ID_PUBREF_COLOR";
	private static final String XML_DOCTYPE_EXTERNAL_ID_SYSREF_COLOR = "XML_DOCTYPE_EXTERNAL_ID_SYSREF_COLOR";
	private static final String XML_DOCTYPE_EXTERNAL_ID_SYSTEM_COLOR = "XML_DOCTYPE_EXTERNAL_ID_SYSTEM_COLOR";
	private static final String XML_DOCTYPE_INTERNAL_SUBSET_COLOR = "XML_DOCTYPE_INTERNAL_SUBSET_COLOR";
	private static final String XML_DOCTYPE_NAME_COLOR = "XML_DOCTYPE_NAME_COLOR";
	private static final String XML_ELEMENT_DECL_CLOSE_COLOR = "XML_ELEMENT_DECL_CLOSE_COLOR";
	private static final String XML_ELEMENT_DECL_CONTENT_COLOR = "XML_ELEMENT_DECL_CONTENT_COLOR";
	private static final String XML_ELEMENT_DECL_NAME_COLOR = "XML_ELEMENT_DECL_NAME_COLOR";
	private static final String XML_ELEMENT_DECLARATION_COLOR = "XML_ELEMENT_DECLARATION_COLOR";
	private static final String XML_EMPTY_TAG_CLOSE_COLOR = "XML_EMPTY_TAG_CLOSE_COLOR";
	private static final String XML_END_TAG_OPEN_COLOR = "XML_END_TAG_OPEN_COLOR";
	private static final String XML_ENTITY_REFERENCE_COLOR = "XML_ENTITY_REFERENCE_COLOR";
	private static final String XML_PE_REFERENCE_COLOR = "XML_PE_REFERENCE_COLOR";
	private static final String XML_PI_CLOSE_COLOR = "XML_PI_CLOSE_COLOR";
	private static final String XML_PI_CONTENT_COLOR = "XML_PI_CONTENT_COLOR";
	private static final String XML_PI_OPEN_COLOR = "XML_PI_OPEN_COLOR";
	private static final String XML_TAG_ATTRIBUTE_EQUALS_COLOR = "XML_TAG_ATTRIBUTE_EQUALS_COLOR";
	private static final String XML_TAG_ATTRIBUTE_NAME_COLOR = "XML_TAG_ATTRIBUTE_NAME_COLOR";
	private static final String XML_TAG_ATTRIBUTE_VALUE_COLOR = "XML_TAG_ATTRIBUTE_VALUE_COLOR";
	private static final String XML_TAG_CLOSE_COLOR = "XML_TAG_CLOSE_COLOR";
	private static final String XML_TAG_NAME_COLOR = "XML_TAG_NAME_COLOR";
	private static final String XML_TAG_OPEN_COLOR = "XML_TAG_OPEN_COLOR";

	private static final String IDENTIFIER_COLOR = "IDENTIFIER_COLOR";
	private static final String KEYWORD_COLOR = "KEYWORD_COLOR";
	private static final String MULTI_LINE_COMMENT_COLOR = "MULTI_LINE_COMMENT_COLOR";
	private static final String DOUBLE_QUOTE_COLOR = "DOUBLE_QUOTE_COLOR";
	private static final String SINGLE_QUOTE_COLOR = "SINGLE_QUOTE_COLOR";
	private static final String DYNAMIC_SQL_COLOR = "DYNAMIC_SQL_COLOR";
	private static final String UNKNOWN_COLOR = "UNKNOWN_COLOR";
	private static final String PARAMETER_COLOR = "PARAMETER_COLOR";

	private IDocument document;
	private ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
	private long timestamp = -1;
	private List<MyBatisXmlToken> tokens = new ArrayList<>();
	private int index;

	public MyBatisXmlRepairer() {
		addColor(BLOCK_TEXT_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(UNDEFINED_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(WHITE_SPACE_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(XML_ATTLIST_DECL_CLOSE_COLOR, new RGB(0, 0, 0));
		addColor(XML_ATTLIST_DECL_CONTENT_COLOR, new RGB(0, 0, 0));
		addColor(XML_ATTLIST_DECL_NAME_COLOR, new RGB(0, 0, 0));
		addColor(XML_ATTLIST_DECLARATION_COLOR, new RGB(0, 0, 0));
		addColor(XML_CDATA_CLOSE_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(XML_CDATA_OPEN_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(XML_CDATA_TEXT_COLOR, new RGB(255, 255, 255)); // DONE
		addColor(XML_CHAR_REFERENCE_COLOR, new RGB(83, 149, 186)); // DONE
		addColor(XML_COMMENT_CLOSE_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(XML_COMMENT_OPEN_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(XML_COMMENT_TEXT_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(XML_CONTENT_COLOR, new RGB(255, 255, 255)); // DONE
		addColor(XML_DECLARATION_CLOSE_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_DECLARATION_OPEN_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_DOCTYPE_DECLARATION_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_DOCTYPE_DECLARATION_CLOSE_COLOR, new RGB(0, 0, 0));
		addColor(XML_DOCTYPE_EXTERNAL_ID_PUBLIC_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_DOCTYPE_EXTERNAL_ID_PUBREF_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(XML_DOCTYPE_EXTERNAL_ID_SYSREF_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(XML_DOCTYPE_EXTERNAL_ID_SYSTEM_COLOR, new RGB(0, 0, 0));
		addColor(XML_DOCTYPE_INTERNAL_SUBSET_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(XML_DOCTYPE_NAME_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(XML_ELEMENT_DECL_CLOSE_COLOR, new RGB(0, 0, 0));
		addColor(XML_ELEMENT_DECL_CONTENT_COLOR, new RGB(0, 0, 0));
		addColor(XML_ELEMENT_DECL_NAME_COLOR, new RGB(0, 0, 0));
		addColor(XML_ELEMENT_DECLARATION_COLOR, new RGB(0, 0, 0));
		addColor(XML_EMPTY_TAG_CLOSE_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_END_TAG_OPEN_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_ENTITY_REFERENCE_COLOR, new RGB(83, 149, 186)); // DONE
		addColor(XML_PE_REFERENCE_COLOR, new RGB(0, 0, 0));
		addColor(XML_PI_CLOSE_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_PI_CONTENT_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(XML_PI_OPEN_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_TAG_ATTRIBUTE_EQUALS_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(XML_TAG_ATTRIBUTE_NAME_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(XML_TAG_ATTRIBUTE_VALUE_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(XML_TAG_CLOSE_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_TAG_NAME_COLOR, new RGB(232, 191, 106)); // DONE
		addColor(XML_TAG_OPEN_COLOR, new RGB(232, 191, 106)); // DONE

		addColor(IDENTIFIER_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(KEYWORD_COLOR, new RGB(200, 120, 52)); // DONE
		addColor(MULTI_LINE_COMMENT_COLOR, new RGB(128, 128, 128)); // DONE
		addColor(DOUBLE_QUOTE_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(SINGLE_QUOTE_COLOR, new RGB(106, 135, 89)); // DONE
		addColor(DYNAMIC_SQL_COLOR, new RGB(63, 127, 95)); // DONE
		addColor(UNKNOWN_COLOR, new RGB(186, 186, 186)); // DONE
		addColor(PARAMETER_COLOR, new RGB(83, 149, 186)); // DONE
	}

	private void addColor(String target, RGB color) {
		if (!colorRegistry.hasValueFor(target)) {
			colorRegistry.put(target, color);
		}
	}

	@Override
	public void setDocument(IDocument document) {
		this.document = document;
	}

	@Override
	public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent event, boolean documentPartitioningChanged) {
		return new Region(0, document.getLength());
	}

	@Override
	public void createPresentation(TextPresentation presentation, ITypedRegion damageRegion) {
		if (document == null) {
			return;
		}

		if (document instanceof IDocumentExtension4 docExt4) {
			long current = docExt4.getModificationStamp();
			if (timestamp != current) {
				timestamp = current;
				tokens.clear();
				index = 0;

				IStructuredDocument structDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion flatNode = structDoc.getRegionAtCharacterOffset(0);
				while (flatNode != null) {
					ITextRegionList subRegions = flatNode.getRegions();
					for (int i = 0; i < subRegions.size(); i++) {
						ITextRegion subRegion = subRegions.get(i);
						String type = subRegion.getType();
						int offset = flatNode.getStartOffset(subRegion);
						int length = subRegion.getLength();
						tokens.add(new MyBatisXmlToken(type, offset, length));
					}
					flatNode = flatNode.getNext();
				}
			}
		}

		int max = damageRegion.getOffset() + damageRegion.getLength();
		while (index < tokens.size()) {
			MyBatisXmlToken token = tokens.get(index);
			setStyle(presentation, token);
			index++;
			if (token.getOffset() + token.getLength() >= max) {
				break;
			}
		}
	}

	private void setStyle(TextPresentation presentation, MyBatisXmlToken token) {
		TextAttribute attribute = null;
		if ("BLOCK_TEXT".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(BLOCK_TEXT_COLOR), null, SWT.NORMAL);
		} else if ("UNDEFINED".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(UNDEFINED_COLOR), null, SWT.NORMAL);
		} else if ("WHITE_SPACE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(WHITE_SPACE_COLOR), null, SWT.NORMAL);
		} else if ("XML_ATTLIST_DECL_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ATTLIST_DECL_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_ATTLIST_DECL_CONTENT".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ATTLIST_DECL_CONTENT_COLOR), null, SWT.NORMAL);
		} else if ("XML_ATTLIST_DECL_NAME".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ATTLIST_DECL_NAME_COLOR), null, SWT.NORMAL);
		} else if ("XML_ATTLIST_DECLARATION".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ATTLIST_DECLARATION_COLOR), null, SWT.NORMAL);
		} else if ("XML_CDATA_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_CDATA_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_CDATA_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_CDATA_OPEN_COLOR), null, SWT.NORMAL);
		} else if ("XML_CDATA_TEXT".equals(token.getType())) {
			setStyleContent(presentation, token);
			return;
		} else if ("XML_CHAR_REFERENCE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_CHAR_REFERENCE_COLOR), null, SWT.NORMAL);
		} else if ("XML_COMMENT_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_COMMENT_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_COMMENT_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_COMMENT_OPEN_COLOR), null, SWT.NORMAL);
		} else if ("XML_COMMENT_TEXT".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_COMMENT_TEXT_COLOR), null, SWT.NORMAL);
		} else if ("XML_CONTENT".equals(token.getType())) {
			setStyleContent(presentation, token);
			return;
		} else if ("XML_DECLARATION_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DECLARATION_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_DECLARATION_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DECLARATION_OPEN_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_DECLARATION".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_DECLARATION_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_DECLARATION_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_DECLARATION_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_EXTERNAL_ID_PUBLIC".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_EXTERNAL_ID_PUBLIC_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_EXTERNAL_ID_PUBREF".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_EXTERNAL_ID_PUBREF_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_EXTERNAL_ID_SYSREF".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_EXTERNAL_ID_SYSREF_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_EXTERNAL_ID_SYSTEM".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_EXTERNAL_ID_SYSTEM_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_INTERNAL_SUBSET".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_INTERNAL_SUBSET_COLOR), null, SWT.NORMAL);
		} else if ("XML_DOCTYPE_NAME".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_DOCTYPE_NAME_COLOR), null, SWT.NORMAL);
		} else if ("XML_ELEMENT_DECL_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ELEMENT_DECL_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_ELEMENT_DECL_CONTENT".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ELEMENT_DECL_CONTENT_COLOR), null, SWT.NORMAL);
		} else if ("XML_ELEMENT_DECL_NAME".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ELEMENT_DECL_NAME_COLOR), null, SWT.NORMAL);
		} else if ("XML_ELEMENT_DECLARATION".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ELEMENT_DECLARATION_COLOR), null, SWT.NORMAL);
		} else if ("XML_EMPTY_TAG_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_EMPTY_TAG_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_END_TAG_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_END_TAG_OPEN_COLOR), null, SWT.NORMAL);
		} else if ("XML_ENTITY_REFERENCE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_ENTITY_REFERENCE_COLOR), null, SWT.NORMAL);
		} else if ("XML_PE_REFERENCE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_PE_REFERENCE_COLOR), null, SWT.NORMAL);
		} else if ("XML_PI_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_PI_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_PI_CONTENT".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_PI_CONTENT_COLOR), null, SWT.NORMAL);
		} else if ("XML_PI_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_PI_OPEN_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_ATTRIBUTE_EQUALS".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_ATTRIBUTE_EQUALS_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_ATTRIBUTE_NAME".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_ATTRIBUTE_NAME_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_ATTRIBUTE_VALUE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_ATTRIBUTE_VALUE_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_CLOSE".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_CLOSE_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_NAME".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_NAME_COLOR), null, SWT.NORMAL);
		} else if ("XML_TAG_OPEN".equals(token.getType())) {
			attribute = new TextAttribute(colorRegistry.get(XML_TAG_OPEN_COLOR), null, SWT.NORMAL);
		}

		if (attribute != null) {
			presentation.addStyleRange(new StyleRange(token.getOffset(), token.getLength(), attribute.getForeground(),
					attribute.getBackground(), attribute.getStyle()));
		}
	}

	private void setStyleContent(TextPresentation presentation, MyBatisXmlToken xmlToken) {
		try {
			String targetText = document.get(xmlToken.getOffset(), xmlToken.getLength());
			MyAtlasLexer lexer = new MyAtlasLexer(targetText);
			while (lexer.hasNext()) {
				MyAtlasToken token = lexer.next();

				if (token.getType() == MyAtlasTokenType.EOF || token.getType() == MyAtlasTokenType.SPACE) {
					continue;
				}

				int absoluteOffset = xmlToken.getOffset() + token.getOffset();
				TextAttribute attribute = null;
				MyAtlasTokenType type = token.getType();

				if (type == MyAtlasTokenType.IDENTIFIER) {
					attribute = new TextAttribute(colorRegistry.get(IDENTIFIER_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.KEYWORD) {
					attribute = new TextAttribute(colorRegistry.get(KEYWORD_COLOR), null, SWT.BOLD);
				} else if (type == MyAtlasTokenType.MULTI_LINE_COMMENT) {
					attribute = new TextAttribute(colorRegistry.get(MULTI_LINE_COMMENT_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.DOUBLE_QUOTE) {
					attribute = new TextAttribute(colorRegistry.get(DOUBLE_QUOTE_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.SINGLE_QUOTE) {
					attribute = new TextAttribute(colorRegistry.get(SINGLE_QUOTE_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.DYNAMIC_SQL) {
					attribute = new TextAttribute(colorRegistry.get(DYNAMIC_SQL_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.UNKNOWN) {
					attribute = new TextAttribute(colorRegistry.get(UNKNOWN_COLOR), null, SWT.NORMAL);
				} else if (type == MyAtlasTokenType.PARAMETER) {
					attribute = new TextAttribute(colorRegistry.get(PARAMETER_COLOR), null, SWT.NORMAL);
				}

				if (attribute != null) {
					presentation.addStyleRange(new StyleRange(absoluteOffset, token.getLength(),
							attribute.getForeground(), attribute.getBackground(), attribute.getStyle()));
				}
			}

		} catch (BadLocationException e) {
		}
	}
}

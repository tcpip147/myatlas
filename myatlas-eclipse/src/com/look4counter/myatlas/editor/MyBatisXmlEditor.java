package com.look4counter.myatlas.editor;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.internal.genericeditor.ExtensionBasedTextEditor;
import org.eclipse.ui.internal.genericeditor.ExtensionBasedTextViewerConfiguration;

public class MyBatisXmlEditor extends ExtensionBasedTextEditor {
	
	@Override
	protected void setSourceViewerConfiguration(SourceViewerConfiguration configuration) {
		if (configuration instanceof ExtensionBasedTextViewerConfiguration extensionConfiguration) {
			super.setSourceViewerConfiguration(new MyBatisXmlConfiguration(extensionConfiguration));
		} else {
			super.setSourceViewerConfiguration(configuration);
		}
	}
}

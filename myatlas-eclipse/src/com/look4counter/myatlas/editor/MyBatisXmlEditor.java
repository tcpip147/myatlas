package com.look4counter.myatlas.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;

public class MyBatisXmlEditor extends TextEditor {

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setSourceViewerConfiguration(new MyBatisXmlConfiguration());
	}
	
}

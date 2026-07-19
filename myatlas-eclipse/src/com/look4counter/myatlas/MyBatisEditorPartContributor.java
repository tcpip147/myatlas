package com.look4counter.myatlas;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditorActionContributor;

public class MyBatisEditorPartContributor extends TextEditorActionContributor {

	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		if (targetEditor instanceof MyBatisEditorPart editorPart) {
			super.setActiveEditor(editorPart.getEditor());
			return;
		}
		super.setActiveEditor(targetEditor);
	}
}

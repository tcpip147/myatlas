package com.look4counter.myatlas.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.look4counter.myatlas.lsp.CustomFilterDocumentProvider;

public class MyBatisXmlEditor extends TextEditor {

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setSourceViewerConfiguration(new MyBatisXmlConfiguration());
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();

		IDocumentProvider parentProvider = getDocumentProvider();
System.out.println(parentProvider);
		if (parentProvider != null) {
			// 2. 오리지널 Provider를 커스텀 Wrapper로 감싸서 다시 세팅합니다.
			setDocumentProvider(new CustomFilterDocumentProvider(parentProvider));
		}
	}

}

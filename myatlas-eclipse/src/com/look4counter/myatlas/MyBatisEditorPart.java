package com.look4counter.myatlas;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.look4counter.myatlas.editor.MyBatisXmlEditor;
import com.look4counter.myatlas.model.MapperFile;

public class MyBatisEditorPart extends EditorPart implements IReusableEditor {

	private MyBatisEditorPartView view;
	private MyBatisXmlEditor editor;
	private IResourceChangeListener resourceChangeListener;
	private IPropertyListener propertyListener;
	private ViewListener viewListener;

	public MyBatisEditorPart() {
		resourceChangeListener = new ResourceChangeListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		editor = new MyBatisXmlEditor();
		editor.init(site, input);
		setPartName(input.getName());
		propertyListener = (_, propId) -> firePropertyChange(propId);
		editor.addPropertyListener(propertyListener);
	}

	@Override
	public void setInput(IEditorInput input) {
		boolean isDirty = isDirty();
		IDocument older = null;
		String olderContent = null;
		if (editor != null && isDirty) {
			IDocumentProvider provider = editor.getDocumentProvider();
			older = provider.getDocument(getEditorInput());
			olderContent = older.get();
		}
		super.setInput(input);
		if (editor != null) {
			editor.setInput(input);
			if (isDirty) {
				IDocumentProvider provider = editor.getDocumentProvider();
				IDocument newer = provider.getDocument(input);
				newer.set(olderContent);
			}
		}
		setPartName(input.getName());
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		if (view != null) {
			view.removeViewListener(viewListener);
			view.dispose();
		}
		if (editor != null) {
			editor.removePropertyListener(propertyListener);
			editor.dispose();
		}
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent) {
		view = new MyBatisEditorPartView(parent);
		viewListener = (message) -> {

		};
		view.addViewListener(viewListener);
		view.loadXmlEditor(editor);
		try {
			MapperFile file = new MapperFile(getEditorInput().getAdapter(IFile.class).getContents());
			view.loadFile(file);
		} catch (CoreException e) {
		}
	}

	@Override
	public void setFocus() {
		if (view != null && !view.isDisposed()) {
			view.setFocus();
		}
	}

	@Override
	public boolean isDirty() {
		if (editor == null) {
			return false;
		}
		return editor.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException("Save As is not supported.");
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		editor.doSave(monitor);
	}

	public MyBatisXmlEditor getEditor() {
		return editor;
	}
}

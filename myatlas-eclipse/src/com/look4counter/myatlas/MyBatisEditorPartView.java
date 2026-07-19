package com.look4counter.myatlas;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.look4counter.myatlas.editor.MyBatisXmlEditor;
import com.look4counter.myatlas.model.MapperFile;

public class MyBatisEditorPartView extends SashForm {

	private Composite left;
	private Composite right;

	private List<ViewListener> viewListeners = new ArrayList<>();

	public MyBatisEditorPartView(Composite parent) {
		super(parent, SWT.HORIZONTAL);

		createLeftPanel();
		createRightPanel();

		setSashWidth(5);
		setWeights(220, 80);
	}

	private void createLeftPanel() {
		left = new Composite(this, SWT.NONE);
		left.setLayout(new FillLayout());
	}

	private void createRightPanel() {
		right = new Composite(this, SWT.NONE);
	}

	public void loadXmlEditor(MyBatisXmlEditor editor) {
		editor.createPartControl(left);
	}

	public void addViewListener(ViewListener viewListener) {
		viewListeners.add(viewListener);
	}

	public void removeViewListener(ViewListener viewListener) {
		viewListeners.remove(viewListener);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void loadFile(MapperFile file) {

	}
}

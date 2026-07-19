package com.look4counter.myatlas.lsp;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

public class CustomFilterDocumentProvider implements IDocumentProvider {

	private final IDocumentProvider delegate;

	public CustomFilterDocumentProvider(IDocumentProvider delegate) {
		this.delegate = delegate;
	}

	@Override
	public IAnnotationModel getAnnotationModel(Object element) {
		// 원래 이클립스나 타사 LSP가 만들어서 넘겨주려던 원본 에러 모델을 받습니다.
		IAnnotationModel originalModel = delegate.getAnnotationModel(element);

		if (originalModel == null)
			return null;

		// 원본 모델을 화면에 바로 바인딩하지 않고, 추가되는 순간을 가로채는 익명 클래스로 감쌉니다.
		return new ProjectionAnnotationModel() {
			@Override
			public void addAnnotation(Annotation annotation, Position position) {
				System.out.println(annotation);
				System.out.println(position);
				super.addAnnotation(annotation, position);
			}

		};
	}

	// 위 인터페이스를 만족하기 위해 나머지 메서드들은 단순히 위임(Delegate)해 줍니다.
	@Override
	public void connect(Object el) throws CoreException {
		delegate.connect(el);
	}

	@Override
	public void disconnect(Object el) {
		delegate.disconnect(el);
	}

	@Override
	public IDocument getDocument(Object el) {
		return delegate.getDocument(el);
	}

	@Override
	public void resetDocument(Object el) throws CoreException {
		delegate.resetDocument(el);
	}

	@Override
	public void saveDocument(org.eclipse.core.runtime.IProgressMonitor m, Object el, IDocument d, boolean o)
			throws CoreException {
		delegate.saveDocument(m, el, d, o);
	}

	@Override
	public long getModificationStamp(Object el) {
		return delegate.getModificationStamp(el);
	}

	@Override
	public long getSynchronizationStamp(Object el) {
		return delegate.getSynchronizationStamp(el);
	}

	@Override
	public boolean isDeleted(Object el) {
		return delegate.isDeleted(0);
	}

	@Override
	public boolean mustSaveDocument(Object el) {
		return delegate.mustSaveDocument(el);
	}

	@Override
	public boolean canSaveDocument(Object el) {
		return delegate.canSaveDocument(el);
	}

	@Override
	public void aboutToChange(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addElementStateListener(IElementStateListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changed(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeElementStateListener(IElementStateListener arg0) {
		// TODO Auto-generated method stub

	}
}

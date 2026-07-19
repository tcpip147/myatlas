package com.look4counter.myatlas.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.internal.genericeditor.ExtensionBasedTextViewerConfiguration;

import com.look4counter.myatlas.lsp.MyBatisXmlProcessor;

public class MyBatisXmlConfiguration extends TextSourceViewerConfiguration {
	private final ExtensionBasedTextViewerConfiguration delegate;

	public MyBatisXmlConfiguration(ExtensionBasedTextViewerConfiguration delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		MyBatisXmlProcessor myLspProcessor = new MyBatisXmlProcessor();
		String defaultPartition = IDocument.DEFAULT_CONTENT_TYPE;
		assistant.setContentAssistProcessor(myLspProcessor, defaultPartition);

		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);
		assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		return assistant;
	}

	@Override
	public org.eclipse.jface.text.presentation.IPresentationReconciler getPresentationReconciler(ISourceViewer v) {
		return delegate.getPresentationReconciler(v);
	}

	@Override
	public org.eclipse.jface.text.reconciler.IReconciler getReconciler(ISourceViewer v) {
		return delegate.getReconciler(v);
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer v) {
		return delegate.getConfiguredContentTypes(v);
	}
}

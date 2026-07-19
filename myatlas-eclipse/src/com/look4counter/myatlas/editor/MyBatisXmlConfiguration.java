package com.look4counter.myatlas.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.look4counter.myatlas.lsp.MyBatisXmlAssistProcessor;

public class MyBatisXmlConfiguration extends TextSourceViewerConfiguration {

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		MyBatisXmlAssistProcessor processor = new MyBatisXmlAssistProcessor();
		String defaultPartition = IDocument.DEFAULT_CONTENT_TYPE;
		assistant.setContentAssistProcessor(processor, defaultPartition);

		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);
		assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);

		return assistant;
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		IAnnotationHover annotation = super.getAnnotationHover(sourceViewer);
		System.out.println(annotation);
		return annotation;
	}
}

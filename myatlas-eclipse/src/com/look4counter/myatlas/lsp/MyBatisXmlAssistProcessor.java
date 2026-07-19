package com.look4counter.myatlas.lsp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.lsp4e.LanguageServerWrapper;
import org.eclipse.lsp4e.operations.completion.LSCompletionProposal;
import org.eclipse.lsp4e.operations.completion.LSContentAssistProcessor;

public class MyBatisXmlAssistProcessor extends LSContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		ICompletionProposal[] proposals = super.computeCompletionProposals(viewer, offset);

		List<ICompletionProposal> filteredList = new ArrayList<>();

		for (ICompletionProposal proposal : proposals) {
			if (proposal instanceof LSCompletionProposal lspProposal) {
				Field wrapperField;
				try {
					wrapperField = LSCompletionProposal.class.getDeclaredField("languageServerWrapper");
					wrapperField.setAccessible(true);

					LanguageServerWrapper wrapper = (LanguageServerWrapper) wrapperField.get(proposal);

					if ("com.look4counter.myatlas.lsp".equals(wrapper.serverDefinition.id)) {
						filteredList.add(lspProposal);
					}
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return filteredList.toArray(new ICompletionProposal[0]);
	}
}

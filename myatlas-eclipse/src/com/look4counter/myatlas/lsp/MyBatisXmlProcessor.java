package com.look4counter.myatlas.lsp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.lsp4e.operations.completion.LSContentAssistProcessor;

public class MyBatisXmlProcessor extends LSContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		// 부모(LSContentAssistProcessor)의 오리지널 로직을 실행하여 전체 제안을 긁어옵니다.
		ICompletionProposal[] proposals = super.computeCompletionProposals(viewer, offset);
		if (proposals == null)
			return null;

		List<ICompletionProposal> cleanProposals = new ArrayList<>();
		for (ICompletionProposal proposal : proposals) {
			String pClass = proposal.getClass().getName().toLowerCase();
			String pStr = proposal.toString().toLowerCase();
System.out.println(pClass);
			// 🎯 LemminX 혹은 Wild Web Developer 관련 패키지나 텍스트가 묻어있다면 탈락시킵니다.
			if (pClass.contains("lemminx") || pClass.contains("wildwebdeveloper") || pStr.contains("lemminx")) {
				continue;
			}
			cleanProposals.add(proposal);
		}

		return cleanProposals.toArray(new ICompletionProposal[0]);
	}
}

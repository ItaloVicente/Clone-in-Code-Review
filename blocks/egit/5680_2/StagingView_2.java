package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;

public abstract class CommitMessageArea extends SpellcheckableMessageArea {

	public CommitMessageArea(Composite parent, String initialText, int styles) {
		super(parent, initialText, styles);
	}

	protected IContentAssistant createContentAssistant(ISourceViewer viewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.enableAutoInsert(true);
		final CommitProposalProcessor processor = getCommitProposalProcessor();
		getTextWidget().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				processor.dispose();
			}
		});
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		return assistant;
	}

	protected abstract CommitProposalProcessor getCommitProposalProcessor();
}

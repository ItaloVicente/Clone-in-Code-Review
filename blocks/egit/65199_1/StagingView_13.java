package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.ICommitEditorProvider;
import org.eclipse.egit.ui.ICommitMessageEditor;
import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.swt.widgets.Composite;

public class DefaultCommitEditorProvider implements ICommitEditorProvider {

	@Override
	public ICommitMessageEditor getEditor(Composite parent, String initialText,
			int styles, final CommitProposalProcessor commitProposalProcessor) {
		return new CommitMessageArea(parent, initialText, styles) {

			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}

		};
	}
}

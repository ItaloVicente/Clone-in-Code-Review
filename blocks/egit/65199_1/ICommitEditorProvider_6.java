package org.eclipse.egit.ui;

import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.swt.widgets.Composite;

public interface ICommitEditorProvider {

	ICommitMessageEditor getEditor(Composite parent, String initialText,
			int styles, CommitProposalProcessor commitProposalProcessor);

}

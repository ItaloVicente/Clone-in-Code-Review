package org.eclipse.egit.ui;

import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public interface ICommitEditorProvider {

	ICommitMessageEditor getEditor(Composite parent,
			@NonNull String initialText,
			int styles, CommitProposalProcessor commitProposalProcessor);

	boolean isEnabled();
}

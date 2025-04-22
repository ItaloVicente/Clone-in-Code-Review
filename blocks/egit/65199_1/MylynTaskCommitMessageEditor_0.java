package org.eclipse.egit.internal.mylyn.ui.commit;

import org.eclipse.egit.ui.ICommitEditorProvider;
import org.eclipse.egit.ui.ICommitMessageEditor;
import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.swt.widgets.Composite;

public class MylynCommitEditorProvider implements ICommitEditorProvider {

	@Override
	public ICommitMessageEditor getEditor(Composite parent, String initialText,
			int styles, final CommitProposalProcessor commitProposalProcessor) {
		return new MylynTaskCommitMessageEditor(parent, styles);
	}
}

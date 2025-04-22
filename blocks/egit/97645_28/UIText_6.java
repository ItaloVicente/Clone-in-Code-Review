package org.eclipse.egit.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.dialogs.CommitDialog;

public interface ICommitMessageProvider2 extends ICommitMessageProvider {

	CommitMessageWithCaretPosition getCommitMessageWithPosition(
			IResource[] resources);

}

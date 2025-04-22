package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class CreateBranchDialog extends AbstractBranchSelectionDialog {
	public CreateBranchDialog(Shell parentShell, Repository repo) {
		super(parentShell, repo);
		setRootsToShow(true, true, true, true);
	}

	@Override
	protected String getMessageText() {
		return UIText.CreateBranchDialog_SelectRefMessage;
	}

	@Override
	protected String getTitle() {
		return UIText.CreateBranchDialog_DialogTitle;
	}

	@Override
	protected String getWindowTitle() {
		return UIText.CreateBranchDialog_WindowTitle;
	}

	@Override
	protected void refNameSelected(String refName) {
		getButton(Window.OK).setEnabled(refName != null);
	}
}

package org.eclipse.egit.ui.internal.dialogs;

import java.io.IOException;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class CheckoutDialog extends AbstractBranchSelectionDialog {
	private String currentBranch;

	public CheckoutDialog(Shell parentShell, Repository repo) {
		super(parentShell, repo, SHOW_LOCAL_BRANCHES | SHOW_REMOTE_BRANCHES
				| SHOW_TAGS | SHOW_REFERENCES | EXPAND_LOCAL_BRANCHES_NODE);
		try {
			currentBranch = repo.getFullBranch();
		} catch (IOException e) {
			currentBranch = null;
		}
	}

	@Override
	protected String getMessageText() {
		return UIText.CheckoutDialog_Message;
	}

	@Override
	protected String getTitle() {
		return UIText.CheckoutDialog_Title;
	}

	@Override
	protected String getWindowTitle() {
		return UIText.CheckoutDialog_WindowTitle;
	}

	@Override
	protected void refNameSelected(String refName) {
		getButton(Window.OK).setEnabled(
				refName != null && !refName.equals(currentBranch));
	}
}

package org.eclipse.egit.ui.internal.dialogs;

import java.io.IOException;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class RebaseTargetSelectionDialog extends AbstractBranchSelectionDialog {

	public RebaseTargetSelectionDialog(Shell parentShell, Repository repo) {
		super(parentShell, repo);
		setRootsToShow(true, true, false);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(Window.OK).setText(
				UIText.RebaseTargetSelectionDialog_RebaseButton);
	}

	@Override
	protected String getMessageText() {
		return UIText.RebaseTargetSelectionDialog_DialogMessage;
	}

	@Override
	protected String getTitle() {
		return UIText.RebaseTargetSelectionDialog_DialogTitle;
	}

	@Override
	protected String getWindowTitle() {
		return NLS.bind(UIText.RebaseTargetSelectionDialog_RebaseTitle, repo
				.getDirectory().toString());
	}

	@Override
	protected void refNameSelected(String refName) {
		boolean tagSelected = refName != null
				&& refName.startsWith(Constants.R_TAGS);

		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES));

		boolean currentSelected;
		try {
			currentSelected = refName != null
					&& refName.equals(repo.getFullBranch());
		} catch (IOException e) {
			currentSelected = false;
		}

		getButton(Window.OK).setEnabled(
				!currentSelected && (branchSelected || tagSelected));
	}
}

package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class BranchEditDialog extends LocalBranchSelectionDialog {
	public BranchEditDialog(Shell shell, Repository repository,
			String branchToMark) {
		super(shell, repository, branchToMark);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createRenameButton(parent);
		createDeleteButton(parent);

		createButton(parent, IDialogConstants.CANCEL_ID,
				UIText.BranchSelectionAndEditDialog_OkClose, true);
	}
}

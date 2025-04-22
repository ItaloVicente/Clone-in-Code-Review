package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class BranchEditDialog extends BranchSelectionAndEditDialog {

	public BranchEditDialog(Shell parentShell, Repository repository,
			String branchToMark) {
		super(parentShell, repository, branchToMark, SHOW_LOCAL_BRANCHES
				| EXPAND_LOCAL_BRANCHES_NODE | SELECT_CURRENT_REF);
	}

	@Override
	protected String getTitle() {
		return UIText.BranchEditDialog_Title;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button renameButton = createRenameButton(parent);
		Button deleteButton = createDeleteButton(parent);

		if (branchTree.getSelection().isEmpty()) {
			renameButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}

		createButton(parent, IDialogConstants.CANCEL_ID,
				UIText.BranchSelectionAndEditDialog_OkClose, true);
	}

}

package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class MergeTargetSelectionDialog extends BranchSelectionDialog {

	public MergeTargetSelectionDialog(Shell parentShell, Repository repo) {
		super(parentShell, repo);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		confirmationBtn = createButton(parent, IDialogConstants.OK_ID,
				UIText.MergeTargetSelectionDialog_ButtonMerge, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected String getRefsLabel() {
		return UIText.MergeTargetSelectionDialog_SelectRef
				+ " " + UIText.MergeTargetSelectionDialog_OnlyFastForward; //$NON-NLS-1$
	}

	@Override
	protected String getTitle() {
		return UIText.MergeTargetSelectionDialog_TitleMerge;
	}

}

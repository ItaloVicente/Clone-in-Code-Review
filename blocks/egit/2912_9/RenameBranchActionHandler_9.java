package org.eclipse.egit.ui.internal.actions;

public class RenameBranchAction extends RepositoryAction {
	public RenameBranchAction() {
		super(ActionCommands.RENAME_BRANCH_ACTION,
				new RenameBranchActionHandler());
	}
}

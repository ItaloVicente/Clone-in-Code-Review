package org.eclipse.egit.ui.internal.actions;

public class DeleteBranchAction extends RepositoryAction {
	public DeleteBranchAction() {
		super(ActionCommands.DELETE_BRANCH_ACTION,
				new DeleteBranchActionHandler());
	}
}

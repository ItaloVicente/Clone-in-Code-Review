package org.eclipse.egit.ui.internal.actions;

public class CreateBranchOnCommitAction extends RepositoryAction {
	public CreateBranchOnCommitAction() {
		super(ActionCommands.CREATE_BRANCH,
				new CreateBranchOnCommitActionHandler());
	}
}

package org.eclipse.egit.ui.internal.actions;


public class PullCurrentBranchAction extends RepositoryAction {
	public PullCurrentBranchAction() {
		super(ActionCommands.PULL_CURRENT_BRANCH, new PullCurrentBranchActionHandler());
	}
}

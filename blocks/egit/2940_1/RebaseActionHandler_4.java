package org.eclipse.egit.ui.internal.actions;

public class RebaseAction extends RepositoryAction {

	public RebaseAction() {
		super(ActionCommands.REBASE_ACTION, new RebaseActionHandler());
	}

}

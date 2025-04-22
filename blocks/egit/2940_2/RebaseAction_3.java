package org.eclipse.egit.ui.internal.actions;

public class RebaseAbortAction extends RepositoryAction {

	public RebaseAbortAction() {
		super(ActionCommands.REBASE_ABORT_ACTION, new CommandActionHandler(
				"org.eclipse.egit.ui.RepositoriesViewAbortRebase")); //$NON-NLS-1$
	}

}

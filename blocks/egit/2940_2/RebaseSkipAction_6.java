package org.eclipse.egit.ui.internal.actions;

public class RebaseContinueAction extends RepositoryAction {

	public RebaseContinueAction() {
		super(ActionCommands.REBASE_CONTINUE_ACTION, new CommandActionHandler(
				"org.eclipse.egit.ui.RepositoriesViewContinueRebase")); //$NON-NLS-1$
	}

}

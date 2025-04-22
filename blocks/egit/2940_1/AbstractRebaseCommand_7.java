package org.eclipse.egit.ui.internal.actions;

public class RebaseSkipAction extends RepositoryAction {

	public RebaseSkipAction() {
		super(ActionCommands.REBASE_SKIP_ACTION, new CommandActionHandler(
				"org.eclipse.egit.ui.RepositoriesViewSkipRebase")); //$NON-NLS-1$
	}

}

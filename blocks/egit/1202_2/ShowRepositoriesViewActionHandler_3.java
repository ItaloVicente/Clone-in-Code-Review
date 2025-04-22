package org.eclipse.egit.ui.internal.actions;

public class ShowRepositoriesViewAction extends RepositoryAction {
	public ShowRepositoriesViewAction() {
		super(ActionCommands.SHOW_REPO_VIEW, new ShowRepositoriesViewActionHandler());
	}
}

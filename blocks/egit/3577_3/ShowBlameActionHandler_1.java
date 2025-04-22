package org.eclipse.egit.ui.internal.actions;

public class ShowBlameAction extends RepositoryAction {

	public ShowBlameAction() {
		super(ActionCommands.SHOW_BLAME_ACTION,
				new ShowBlameActionHandler());
	}

}

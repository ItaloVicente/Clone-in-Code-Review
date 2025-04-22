package org.eclipse.egit.ui.internal.actions;


public class ShowHistoryAction extends RepositoryAction {
	public ShowHistoryAction() {
		super(ActionCommands.SHOW_HISTORY, new ShowHistoryActionHandler());
	}
}

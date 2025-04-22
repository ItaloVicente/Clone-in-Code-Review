package org.eclipse.egit.ui.internal.actions;

public class DisconnectAction extends RepositoryAction {
	public DisconnectAction() {
		super(ActionCommands.DISCONNECT_ACTION, new DisconnectActionHandler());
	}
}

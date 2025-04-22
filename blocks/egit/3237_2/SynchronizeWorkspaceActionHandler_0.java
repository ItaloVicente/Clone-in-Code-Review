package org.eclipse.egit.ui.internal.actions;

public class SynchronizeWorkspaceAction extends RepositoryAction {
	public SynchronizeWorkspaceAction() {
		super(ActionCommands.SYNC_WORKSPACE_ACTION, new SynchronizeWorkspaceActionHandler());
	}
}

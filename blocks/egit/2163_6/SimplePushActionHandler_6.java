package org.eclipse.egit.ui.internal.actions;

public class SimplePushAction extends RepositoryAction {
	public SimplePushAction() {
		super(ActionCommands.SIMPLE_PUSH_ACTION, new SimplePushActionHandler());
	}
}

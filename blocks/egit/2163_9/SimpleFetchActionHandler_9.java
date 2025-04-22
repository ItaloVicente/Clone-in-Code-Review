package org.eclipse.egit.ui.internal.actions;

public class SimpleFetchAction extends RepositoryAction {
	public SimpleFetchAction() {
		super(ActionCommands.SIMPLE_FETCH_ACTION, new SimpleFetchActionHandler());
	}
}

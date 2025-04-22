package org.eclipse.egit.ui.internal.actions;

public class ReplaceWithPreviousAction extends RepositoryAction {

	public ReplaceWithPreviousAction() {
		super(ActionCommands.REPLACE_WITH_PREVIOUS_ACTION,
				new ReplaceWithPreviousActionHandler());
	}
}

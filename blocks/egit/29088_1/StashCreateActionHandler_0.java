package org.eclipse.egit.ui.internal.actions;

public class StashCreateAction extends RepositoryAction {
	public StashCreateAction() {
		super(ActionCommands.STASH_CREATE_ACTION,
				new StashCreateActionHandler());
	}
}

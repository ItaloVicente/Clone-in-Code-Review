package org.eclipse.egit.ui.internal.actions;

public class ReplaceWithRefAction extends RepositoryAction {

	public ReplaceWithRefAction() {
		super(ActionCommands.REPLACE_WITH_REF_ACTION,
				new ReplaceWithRefActionHandler());
	}

}

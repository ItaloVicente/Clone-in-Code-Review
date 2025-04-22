package org.eclipse.egit.ui.internal.actions;

public class ReplaceWithHeadAction extends RepositoryAction {

	public ReplaceWithHeadAction() {
		super(ActionCommands.REPLACE_WITH_HEAD_ACTION,
				new ReplaceWithHeadActionHandler());
	}

}

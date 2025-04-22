package org.eclipse.egit.ui.internal.actions;

public class ReplaceWithCommitAction extends RepositoryAction {

	public ReplaceWithCommitAction() {
		super(ActionCommands.REPLACE_WITH_COMMIT_ACTION,
				new ReplaceWithCommitActionHandler());
	}

}

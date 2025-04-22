package org.eclipse.egit.ui.internal.actions;

public class PullWithOptionsAction extends RepositoryAction {

	public PullWithOptionsAction() {
		super(ActionCommands.PULL_WITH_OPTIONS,
				new PullWithOptionsActionHandler());
	}

}

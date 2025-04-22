package org.eclipse.egit.ui.internal.actions;

public class CreateTagOnCommitAction extends RepositoryAction {
	public CreateTagOnCommitAction() {
		super(ActionCommands.CREATE_TAG, new CreateTagOnCommitActionHandler());
	}
}

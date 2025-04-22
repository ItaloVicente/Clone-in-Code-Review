package org.eclipse.egit.ui.internal.actions;

public class CreatePatchAction extends RepositoryAction {

	public CreatePatchAction() {
		super(ActionCommands.CREATE_PATCH, new CreatePatchActionHandler());
	}
}

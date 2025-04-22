
package org.eclipse.egit.ui.internal.actions;

public class CreateStashAction extends RepositoryAction {

	public CreateStashAction() {
		super(ActionCommands.CREATE_STASH, new CreateStashActionHandler());
	}
}

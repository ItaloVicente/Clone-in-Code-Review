
package org.eclipse.egit.ui.internal.actions;

public class ApplyStashAction extends RepositoryAction {

	public ApplyStashAction() {
		super(ActionCommands.APPLY_STASH, new ApplyStashActionHandler());
	}
}

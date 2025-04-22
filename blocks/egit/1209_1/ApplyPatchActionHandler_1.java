package org.eclipse.egit.ui.internal.actions;


public class ApplyPatchAction extends RepositoryAction {
	public ApplyPatchAction() {
		super(ActionCommands.APPLY_PATCH, new ApplyPatchActionHandler());
	}
}

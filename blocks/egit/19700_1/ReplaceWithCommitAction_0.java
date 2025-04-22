package org.eclipse.egit.ui.internal.actions;

public class DiscardChangesAction extends RepositoryAction {
	public DiscardChangesAction() {
		super(ActionCommands.DISCARD_CHANGES_ACTION, new DiscardChangesActionHandler());
	}
}

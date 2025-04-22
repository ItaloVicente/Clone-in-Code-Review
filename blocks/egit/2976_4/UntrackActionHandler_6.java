package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.core.op.TagOperation;

public class UntrackAction extends RepositoryAction {
	public UntrackAction() {
		super(ActionCommands.UNTRACK_ACTION, new UntrackActionHandler());
	}
}

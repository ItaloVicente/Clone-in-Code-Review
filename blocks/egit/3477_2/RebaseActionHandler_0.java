package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.core.op.RebaseOperation;

public class RebaseAction extends RepositoryAction {

	public RebaseAction() {
		super(ActionCommands.REBASE_ACTION, new RebaseActionHandler());
	}

}

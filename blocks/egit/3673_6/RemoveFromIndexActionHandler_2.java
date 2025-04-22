package org.eclipse.egit.ui.internal.actions;

import static org.eclipse.egit.ui.internal.actions.ActionCommands.REMOVE_FROM_INDEX;

import org.eclipse.egit.core.op.RemoveFromIndexOperation;


public class RemoveFromIndexAction extends RepositoryAction {

	public RemoveFromIndexAction() {
		super(REMOVE_FROM_INDEX, new RemoveFromIndexActionHandler());
	}

}

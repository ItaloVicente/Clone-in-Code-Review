package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.op.DiscardChangesOperation;
import org.eclipse.jgit.lib.Constants;

public class ReplaceWithHeadActionHandler extends DiscardChangesActionHandler {

	protected DiscardChangesOperation createOperation(ExecutionEvent event)
			throws ExecutionException {
		return new DiscardChangesOperation(getSelectedResources(event),
				Constants.HEAD);
	}

}

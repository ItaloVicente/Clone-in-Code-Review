package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.op.DiscardChangesOperation;
import org.eclipse.egit.ui.internal.history.CommitSelectionDialog;
import org.eclipse.jface.window.Window;

public class ReplaceWithCommitActionHandler extends DiscardChangesActionHandler {

	@Override
	protected DiscardChangesOperation createOperation(ExecutionEvent event)
			throws ExecutionException {
		CommitSelectionDialog dlg = new CommitSelectionDialog(getShell(event),
				getRepository(true, event));
		return dlg.open() == Window.OK ? new DiscardChangesOperation(
				getSelectedResources(event), dlg.getCommitId().name()) : null;
	}

}

package org.eclipse.ui.operations;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;

public final class UndoRedoActionGroup extends ActionGroup {

	private UndoActionHandler undoActionHandler;

	private RedoActionHandler redoActionHandler;

	public UndoRedoActionGroup(IWorkbenchPartSite site,
			IUndoContext undoContext, boolean pruneHistory) {

		undoActionHandler = new UndoActionHandler(site, undoContext);
		undoActionHandler.setPruneHistory(pruneHistory);

		redoActionHandler = new RedoActionHandler(site, undoContext);
		redoActionHandler.setPruneHistory(pruneHistory);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		if (undoActionHandler != null) {
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					undoActionHandler);
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					redoActionHandler);
		}
	}
}

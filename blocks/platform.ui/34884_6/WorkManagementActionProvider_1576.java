
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

public class UndoRedoActionProvider extends CommonActionProvider {

	private UndoRedoActionGroup undoRedoGroup;

	@Override
	public void init(ICommonActionExtensionSite anActionSite) {
		IUndoContext workspaceContext = (IUndoContext) ResourcesPlugin
				.getWorkspace().getAdapter(IUndoContext.class);
		undoRedoGroup = new UndoRedoActionGroup(((ICommonViewerWorkbenchSite) anActionSite.getViewSite()).getSite(),
				workspaceContext, true);
	}

	@Override
	public void dispose() {
		undoRedoGroup.dispose();
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		undoRedoGroup.fillActionBars(actionBars);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		undoRedoGroup.fillContextMenu(menu);
	}

	@Override
	public void setContext(ActionContext context) {
		undoRedoGroup.setContext(context);
	}

	@Override
	public void updateActionBars() {
		undoRedoGroup.updateActionBars();
	}

}

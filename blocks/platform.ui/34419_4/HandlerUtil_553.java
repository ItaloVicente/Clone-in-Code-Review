
package org.eclipse.ui.handlers;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.ui.IWorkbenchCommandConstants;

public class ExpandAllHandler extends AbstractHandler {
	public static final String COMMAND_ID = IWorkbenchCommandConstants.NAVIGATE_EXPAND_ALL;

	private AbstractTreeViewer treeViewer;

	public ExpandAllHandler(AbstractTreeViewer viewer) {
		Assert.isNotNull(viewer);
		treeViewer = viewer;
	}

	@Override
	public Object execute(ExecutionEvent event) {
		treeViewer.expandAll();
		return null;
	}

	@Override
	public void dispose() {
		treeViewer = null;
	}
}

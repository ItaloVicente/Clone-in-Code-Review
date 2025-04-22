
package org.eclipse.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jface.viewers.AbstractTreeViewer;

import org.eclipse.ui.IWorkbenchCommandConstants;

public class CollapseAllHandler extends AbstractHandler {
	public static final String COMMAND_ID = IWorkbenchCommandConstants.NAVIGATE_COLLAPSE_ALL;

	private AbstractTreeViewer treeViewer;

	public CollapseAllHandler(AbstractTreeViewer viewer) {
		Assert.isNotNull(viewer);
		treeViewer = viewer;
	}

	@Override
	public Object execute(ExecutionEvent event) {
		treeViewer.collapseAll();
		return null;
	}

	@Override
	public void dispose() {
		treeViewer = null;
	}
}

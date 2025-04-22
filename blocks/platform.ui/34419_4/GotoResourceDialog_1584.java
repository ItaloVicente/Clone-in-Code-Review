package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.INavigatorHelpContextIds;

public class GotoResourceAction extends Action {

	protected Shell shell;

	protected TreeViewer viewer;

	public GotoResourceAction(Shell shell, TreeViewer viewer) {
		this.shell = shell;
		this.viewer = viewer;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				INavigatorHelpContextIds.GOTO_RESOURCE_ACTION);
	}

	@Override
	public void run() {
		GotoResourceDialog dialog = new GotoResourceDialog(shell,
				ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE
						| IResource.FOLDER | IResource.PROJECT);
		dialog.open();
		Object[] result = dialog.getResult();
		if (result == null || result.length == 0
				|| result[0] instanceof IResource == false) {
			return;
		}

		IResource selection = (IResource) result[0];
		viewer.setSelection(new StructuredSelection(selection), true);
	}
}

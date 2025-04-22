package org.eclipse.egit.ui.internal.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.op.DeletePathsOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ui.actions.DeleteResourceAction;

public class DeletePathsOperationUI {

	private final Collection<IPath> paths;
	private final IShellProvider shellProvider;

	public DeletePathsOperationUI(final Collection<IPath> paths,
			final IShellProvider shellProvider) {
		this.paths = paths;
		this.shellProvider = shellProvider;
	}

	public void run() {
		List<IResource> resources = getSelectedResourcesIfAllExist();
		if (resources != null)
			runNormalAction(resources);
		else
			runNonWorkspaceAction();
	}

	private void runNormalAction(List<IResource> resources) {
		DeleteResourceAction action = new DeleteResourceAction(shellProvider);
		IStructuredSelection selection = new StructuredSelection(resources);
		action.selectionChanged(selection);
		action.run();
	}

	private void runNonWorkspaceAction() {
		boolean performAction = MessageDialog.openConfirm(
				shellProvider.getShell(),
				UIText.DeleteResourcesOperationUI_confirmActionTitle,
				UIText.DeleteResourcesOperationUI_confirmActionMessage);
		if (!performAction)
			return;

		DeletePathsOperation operation = new DeletePathsOperation(paths);

		try {
			operation.execute(null);
		} catch (CoreException e) {
			Activator.handleError(UIText.DeleteResourcesOperationUI_deleteFailed, e, true);
		}
	}

	private List<IResource> getSelectedResourcesIfAllExist() {
		List<IResource> resources = new ArrayList<>();
		for (IPath path : paths) {
			IResource resource = ResourceUtil.getResourceForLocation(path, false);
			if (resource != null)
				resources.add(resource);
			else
				return null;
		}
		return resources;
	}
}

package org.eclipse.egit.ui.internal.operations;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.op.DeleteResourcesOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ui.actions.DeleteResourceAction;

public class DeleteResourcesOperationUI {

	private final Collection<IResource> resources;
	private final IShellProvider shellProvider;

	public DeleteResourcesOperationUI(final Collection<IResource> resources,
			final IShellProvider shellProvider) {
		this.resources = resources;
		this.shellProvider = shellProvider;
	}

	public void run() {
		if (selectionIncludesNonWorkspaceResources())
			runNonWorkspaceAction();
		else
			runNormalAction();
	}

	private void runNormalAction() {
		DeleteResourceAction action = new DeleteResourceAction(shellProvider);
		IStructuredSelection selection = new StructuredSelection(
				new ArrayList<IResource>(resources));
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

		DeleteResourcesOperation operation = new DeleteResourcesOperation(resources);

		try {
			operation.execute(null);
		} catch (CoreException e) {
			Activator.handleError(UIText.DeleteResourcesOperationUI_deleteFailed, e, true);
		}
	}

	private boolean selectionIncludesNonWorkspaceResources() {
		for (IResource resource : resources)
			if (!resource.exists())
				return true;
		return false;
	}
}

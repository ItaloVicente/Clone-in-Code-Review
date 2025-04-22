package org.eclipse.ui.views.markers.internal;

import java.util.ArrayList;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public abstract class MarkerSelectionProviderAction extends
		SelectionProviderAction {

	public MarkerSelectionProviderAction(ISelectionProvider provider,
			String text) {
		super(provider, text);

	}

	IMarker[] getSelectedMarkers() {

		return getSelectedMarkers(getStructuredSelection());
	}

	IMarker[] getSelectedMarkers(IStructuredSelection structured) {
		Object[] selection = structured.toArray();
		ArrayList markers = new ArrayList();
		for (int i = 0; i < selection.length; i++) {
			Object object = selection[i];
			if (!(object instanceof MarkerNode)) {
				return new IMarker[0];// still pending
			}
			MarkerNode marker = (MarkerNode) object;
			if (marker.isConcrete()) {
				markers.add(((ConcreteMarker) object).getMarker());
			}
		}

		return (IMarker[]) markers.toArray(new IMarker[markers.size()]);
	}

	IMarker getSelectedMarker() {

		ConcreteMarker selection = (ConcreteMarker) getStructuredSelection()
				.getFirstElement();
		return selection.getMarker();
	}

	void execute(IUndoableOperation operation, String title,
			IProgressMonitor monitor, IAdaptable uiInfo) {
		try {
			PlatformUI.getWorkbench().getOperationSupport()
					.getOperationHistory().execute(operation, monitor, uiInfo);
		} catch (ExecutionException e) {
			if (e.getCause() instanceof CoreException) {
				ErrorDialog
						.openError(WorkspaceUndoUtil.getShell(uiInfo), title,
								null, ((CoreException) e.getCause())
										.getStatus());
			} else {
				IDEWorkbenchPlugin.log(title, e);
			}
		}
	}
}


package org.eclipse.ui.views.markers.internal;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

public class ActionOpenMarker extends MarkerSelectionProviderAction {

	private final String IMAGE_PATH = "elcl16/gotoobj_tsk.png"; //$NON-NLS-1$

	private final String DISABLED_IMAGE_PATH = "dlcl16/gotoobj_tsk.png"; //$NON-NLS-1$

	protected IWorkbenchPart part;

	public ActionOpenMarker(IWorkbenchPart part, ISelectionProvider provider) {
		super(provider, MarkerMessages.openAction_title);
		this.part = part;
		setImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor(IMAGE_PATH));
		setDisabledImageDescriptor(IDEWorkbenchPlugin
				.getIDEImageDescriptor(DISABLED_IMAGE_PATH));
		setEnabled(false);
	}

	@Override
	public void run() {
		IMarker[] markers = getSelectedMarkers();
		for (int i = 0; i < markers.length; i++) {
			IMarker marker = markers[i];

			IEditorPart editor = part.getSite().getPage().getActiveEditor();
			if (editor != null) {
				IEditorInput input = editor.getEditorInput();
				IFile file = ResourceUtil.getFile(input);
				if (file != null) {
					if (marker.getResource().equals(file)) {
						part.getSite().getPage().activate(editor);
					}
				}
			}

			if (marker.getResource() instanceof IFile) {
				try {
					IFile file = (IFile) marker.getResource();
					if (file.getLocation() == null
							|| file.getLocationURI() == null)
						return; // Abort if it cannot be opened
					IDE.openEditor(part.getSite().getPage(), marker,
							OpenStrategy.activateOnOpen());
				} catch (PartInitException e) {

					CoreException nestedException = null;
					IStatus status = e.getStatus();
					if (status != null
							&& status.getException() instanceof CoreException) {
						nestedException = (CoreException) status.getException();
					}

					if (nestedException != null) {
						reportStatus(nestedException.getStatus());
					} else {
						reportError(e.getLocalizedMessage());
					}
				}
			}
		}
	}

	private void reportError(String message) {
		IStatus status = new Status(IStatus.ERROR,
				IDEWorkbenchPlugin.IDE_WORKBENCH, message);
		reportStatus(status);
	}

	private void reportStatus(IStatus status) {
		StatusAdapter adapter = new StatusAdapter(status);
		adapter.setProperty(StatusAdapter.TITLE_PROPERTY,
				MarkerMessages.OpenMarker_errorTitle);
		StatusManager.getManager().handle(adapter, StatusManager.SHOW);
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (Util.allConcreteSelection(selection)) {
			Iterator nodes = selection.iterator();
			while (nodes.hasNext()) {
				ConcreteMarker marker = ((MarkerNode) nodes.next()).getConcreteRepresentative();
				if (marker.getResource().getType() == IResource.FILE) {
					setEnabled(true);
					return;
				}
			}
		}
		setEnabled(false);
	}
}

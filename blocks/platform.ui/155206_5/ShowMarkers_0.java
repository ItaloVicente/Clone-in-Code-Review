package org.eclipse.ui.internal.views.markers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.OpenAndLinkWithEditorHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

final class ShowMarkers extends OpenAndLinkWithEditorHelper {

	private final IWorkbenchPartSite partSite;

	ShowMarkers(StructuredViewer viewer, IWorkbenchPartSite partSite) {
		super(viewer);
		this.partSite = partSite;
		setLinkWithEditor(false);
	}

	@Override
	protected void activate(ISelection selection) {
		open(selection, true);
	}

	@Override
	protected void linkToEditor(ISelection selection) {
	}

	@Override
	protected void open(ISelection selection, boolean activate) {
		IStructuredSelection structured = (IStructuredSelection) selection;
		Object first = structured.getFirstElement();
		if (!(first instanceof IMarker)) {
			return;
		}
		IMarker marker = (IMarker) first;
		if (marker.getResource() instanceof IFile) {
			try {
				IDE.openEditor(partSite.getPage(), marker, activate);
			} catch (PartInitException e) {
				MarkerSupportInternalUtilities.showViewError(e);
			}
		}
	}
}

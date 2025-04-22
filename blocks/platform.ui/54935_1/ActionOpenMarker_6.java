
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.SelectionProviderAction;

public class ActionMarkerProperties extends SelectionProviderAction {

	private IWorkbenchPart part;

	private String markerName;

	public ActionMarkerProperties(IWorkbenchPart part,
			ISelectionProvider provider, String markerName) {
		super(provider, MarkerMessages.propertiesAction_title);
		setEnabled(false);
		this.part = part;
		this.markerName = markerName;
	}

	@Override
	public void run() {
		if (!isEnabled()) {
			return;
		}
		Object obj = getStructuredSelection().getFirstElement();
		if (!(obj instanceof ConcreteMarker)) {
			return;
		}
		ConcreteMarker marker = (ConcreteMarker) obj;
		DialogMarkerProperties dialog = new DialogMarkerProperties(part
				.getSite().getShell(), MarkerMessages.propertiesDialog_title, markerName);
		dialog.setMarker(marker.getMarker());
		dialog.open();
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(selection != null && selection.size() == 1);
	}
}

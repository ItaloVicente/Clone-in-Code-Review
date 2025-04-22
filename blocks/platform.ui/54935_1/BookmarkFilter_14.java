
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class ActionTaskProperties extends MarkerSelectionProviderAction {

	private IWorkbenchPart part;

	public ActionTaskProperties(IWorkbenchPart part, ISelectionProvider provider) {
		super(provider, MarkerMessages.propertiesAction_title);
		setEnabled(false);
		this.part = part;
	}

	@Override
	public void run() {

		DialogMarkerProperties dialog = new DialogTaskProperties(part.getSite()
				.getShell());
		dialog.setMarker(getSelectedMarker());
		dialog.open();
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(Util.isSingleConcreteSelection(selection));
	}
}

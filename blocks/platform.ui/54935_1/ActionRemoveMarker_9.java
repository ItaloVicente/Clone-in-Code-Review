
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class ActionProblemProperties extends MarkerSelectionProviderAction {

    private IWorkbenchPart part;

    public ActionProblemProperties(IWorkbenchPart part,
            ISelectionProvider provider) {
        super(provider, MarkerMessages.propertiesAction_title);
        setEnabled(false);
        this.part = part;
    }

    @Override
	public void run() {
 
    	IMarker marker = getSelectedMarker();
        DialogMarkerProperties dialog = new DialogProblemProperties(part
                .getSite().getShell());
        dialog.setMarker(marker);
        dialog.open();
    }

    @Override
	public void selectionChanged(IStructuredSelection selection) {
        setEnabled(Util.isSingleConcreteSelection(selection));
    }
}

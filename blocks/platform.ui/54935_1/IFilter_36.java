
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

class FiltersAction extends Action {

    private MarkerView view;

    public FiltersAction(MarkerView view) {
        super(MarkerMessages.filtersAction_title);
        setImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("elcl16/filter_ps.png")); //$NON-NLS-1$
        setToolTipText(MarkerMessages.filtersAction_tooltip);
        this.view = view;
        setEnabled(true);
    }

    @Override
	public void run() {
        view.openFiltersDialog();
    }
}

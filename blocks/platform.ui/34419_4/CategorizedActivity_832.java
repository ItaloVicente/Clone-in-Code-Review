package org.eclipse.ui.internal.activities.ws;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

public class ActivityViewerFilter extends ViewerFilter {

    private boolean hasEncounteredFilteredItem = false;

    @Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (WorkbenchActivityHelper.filterItem(element)) {
            setHasEncounteredFilteredItem(true);
            return false;
        }
        return true;
    }

    public boolean getHasEncounteredFilteredItem() {
        return hasEncounteredFilteredItem;
    }

    public void setHasEncounteredFilteredItem(boolean hasEncounteredFilteredItem) {
        this.hasEncounteredFilteredItem = hasEncounteredFilteredItem;
    }
}

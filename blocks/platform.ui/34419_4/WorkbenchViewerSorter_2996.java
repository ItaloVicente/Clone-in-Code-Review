package org.eclipse.ui.model;

import java.util.Comparator;

import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.ViewerComparator;

public class WorkbenchViewerComparator extends ViewerComparator {

    public WorkbenchViewerComparator() {
        super();
    }

    public WorkbenchViewerComparator(Comparator comparator) {
        super(comparator);
    }

    @Override
	public boolean isSorterProperty(Object element, String propertyId) {
        return propertyId.equals(IBasicPropertyConstants.P_TEXT);
    }
}

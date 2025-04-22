package org.eclipse.ui.model;

import java.text.Collator; // can't use ICU, public API

import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.ViewerSorter;

@Deprecated
public class WorkbenchViewerSorter extends ViewerSorter {

    public WorkbenchViewerSorter() {
        super();
    }

    public WorkbenchViewerSorter(Collator collator) {
        super(collator);
    }

    @Override
	public boolean isSorterProperty(Object element, String propertyId) {
        return propertyId.equals(IBasicPropertyConstants.P_TEXT);
    }
}

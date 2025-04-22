package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.ViewerSorter;

public class Sorter extends ViewerSorter {

	@Override
	public boolean isSorterProperty(Object element, String property) {
		return IBasicPropertyConstants.P_TEXT.equals(property);
	}
}

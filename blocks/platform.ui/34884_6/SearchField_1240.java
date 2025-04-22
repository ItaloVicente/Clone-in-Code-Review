
package org.eclipse.ui.internal.quickaccess;

import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jface.resource.ImageDescriptor;

public abstract class QuickAccessProvider {

	private QuickAccessElement[] sortedElements;

	public abstract String getId();

	public abstract String getName();

	public abstract ImageDescriptor getImageDescriptor();

	public abstract QuickAccessElement[] getElements();

	public QuickAccessElement[] getElementsSorted() {
		if (sortedElements == null) {
			sortedElements = getElements();
			Arrays.sort(sortedElements, new Comparator<QuickAccessElement>() {
				@Override
				public int compare(QuickAccessElement e1, QuickAccessElement e2) {
					return e1.getSortLabel().compareTo(e2.getSortLabel());
				}
			});
		}
		return sortedElements;
	}
	
	public abstract QuickAccessElement getElementForId(String id);

	public boolean isAlwaysPresent() {
		return false;
	}

	public void reset() {
		sortedElements = null;
		doReset();
	}

	protected abstract void doReset();
}


package org.eclipse.ui.model;

import java.util.Comparator;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class ContributionComparator extends ViewerComparator implements
		Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		IComparableContribution c1 = null, c2 = null;
		
		if (o1 instanceof IComparableContribution)
			c1 = (IComparableContribution) o1;
		
		if (o2 instanceof IComparableContribution)
			c2 = (IComparableContribution) o2;
		
		if (c1 == null && c2 == null) {
			String s1 = getComparisonString(o1); 
			String s2 = getComparisonString(o2);
			
			return Policy.getComparator().compare(s1, s2);
		}
		
		if (c1 == null)
			return 1;
		if (c2 == null)
			return -1;
		return compare(c1, c2);
	}

	private String getComparisonString(Object o) {
		if (o instanceof IPreferenceNode) {
			return ((IPreferenceNode)o).getLabelText();
		}
		return o.toString();
	}

	public int compare(IComparableContribution c1, IComparableContribution c2) {
		int cat1 = category(c1);
		int cat2 = category(c2);

		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		String name1 = c1.getLabel();
		String name2 = c2.getLabel();

		if (name1 == null) {
			name1 = "";//$NON-NLS-1$
		}
		if (name2 == null) {
			name2 = "";//$NON-NLS-1$
		}

		return Policy.getComparator().compare(name1, name2);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return compare(e1, e2);
	}

	public int category(IComparableContribution c) {
		return c.getPriority();
	}

	@Override
	public int category(Object element) {
		return category((IComparableContribution) element);
	}
}


package org.eclipse.ui.internal.navigator.extensions;

import java.util.Comparator;

import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentExtension;

public class ExtensionSequenceNumberComparator implements Comparator {

	public static final ExtensionSequenceNumberComparator INSTANCE = new ExtensionSequenceNumberComparator(true);

	public static final ExtensionSequenceNumberComparator DESCENDING = new ExtensionSequenceNumberComparator(false);
	
	private final int sortAscending;
	
	public ExtensionSequenceNumberComparator(boolean toSortAscending) {
		sortAscending = toSortAscending ? 1 : -1; 
	}

	@Override
	public int compare(Object o1, Object o2) {

		INavigatorContentDescriptor lvalue = null;
		INavigatorContentDescriptor rvalue = null;

		if (o1 instanceof INavigatorContentDescriptor) {
			lvalue = (INavigatorContentDescriptor) o1;
		} else if (o1 instanceof NavigatorContentExtension) {
			lvalue = ((NavigatorContentExtension) o1).getDescriptor();
		}

		if (o2 instanceof INavigatorContentDescriptor) {
			rvalue = (INavigatorContentDescriptor) o2;
		} else if (o2 instanceof INavigatorContentExtension) {
			rvalue = ((NavigatorContentExtension) o2).getDescriptor();
		}

		if (lvalue == null || rvalue == null) {
			return  -1 * sortAscending;
		}

		int c = lvalue.getSequenceNumber() - rvalue.getSequenceNumber();
		if (c != 0) {
			return c * sortAscending;
		}
		return 0;

	}

}

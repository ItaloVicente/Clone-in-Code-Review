
package org.eclipse.ui.navigator;

import java.util.Map;

import org.eclipse.jface.viewers.ViewerSorter;

public interface INavigatorSorterService {

	ViewerSorter findSorterForParent(Object aParent);

	ViewerSorter findSorter(INavigatorContentDescriptor source, Object parent,
			Object lvalue, Object rvalue);

	public Map findAvailableSorters(INavigatorContentDescriptor theSource);

}


package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.ViewerFilter;

public interface INavigatorFilterService {

	ViewerFilter[] getVisibleFilters(boolean toReturnOnlyActiveFilters);

	ICommonFilterDescriptor[] getVisibleFilterDescriptors();

	boolean isActive(String aFilterId);

	public void activateFilterIdsAndUpdateViewer(String[] theFilterIds);

	void setActiveFilterIds(String[] theFilterIds);

	void persistFilterActivationState();
	
	ViewerFilter getViewerFilter(ICommonFilterDescriptor theDescriptor);
	
}

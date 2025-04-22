
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.internal.navigator.NavigatorFilterService;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonFilterContentProvider implements IStructuredContentProvider {

	private INavigatorContentService contentService;
	private Object[] NO_ELEMENTS = new Object[0];
 
 
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof INavigatorContentService) {
			contentService = (INavigatorContentService) newInput;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(contentService != null) {
			NavigatorFilterService filterService = (NavigatorFilterService) contentService.getFilterService();
			return filterService.getVisibleFilterDescriptorsForUI();
		}
		return NO_ELEMENTS ;
		
	}

	@Override
	public void dispose() {

	}

}

package org.eclipse.ui.internal.navigator.resources;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.navigator.AdaptabilityUtility;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.filters.UserFilter;
import org.eclipse.ui.navigator.CommonViewer;

public class ResourceNameRegexpFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		IResource resource = (IResource) AdaptabilityUtility.getAdapter(element, IResource.class);
		if (resource != null && viewer.getData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA) != null) {
			List<UserFilter> filters = (List<UserFilter>)viewer.getData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA);
			for (UserFilter filter : filters) {
				if (filter.isEnabled()) {
					if (Pattern.matches(filter.getRegexp(), resource.getName())) {
						return false;
					}
				}
			}
		}
		return true;
	}

}

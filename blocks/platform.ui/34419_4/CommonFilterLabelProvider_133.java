
package org.eclipse.ui.internal.navigator.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentRegistryReader;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonFilterDescriptorManager {

	private static final CommonFilterDescriptorManager INSTANCE = new CommonFilterDescriptorManager();

	private static final CommonFilterDescriptor[] NO_FILTER_DESCRIPTORS = new CommonFilterDescriptor[0];

	private final Map<String, CommonFilterDescriptor> filters = new HashMap<String, CommonFilterDescriptor>();

	public static CommonFilterDescriptorManager getInstance() {
		return INSTANCE;
	}

	private CommonFilterDescriptorManager() {
		new CommonFilterDescriptorRegistry().readRegistry();
	}

	public static final boolean FOR_UI = true;
	
	public CommonFilterDescriptor[] findVisibleFilters(INavigatorContentService contentService) {
		return findVisibleFilters(contentService, !FOR_UI);
	}

	public CommonFilterDescriptor[] findVisibleFilters(INavigatorContentService contentService, boolean forUI) {

		List<CommonFilterDescriptor> visibleFilters = new ArrayList<CommonFilterDescriptor>();
		CommonFilterDescriptor descriptor;
		for (Iterator filtersItr = filters.entrySet().iterator(); filtersItr.hasNext();) {
			descriptor = (CommonFilterDescriptor) ((Map.Entry)filtersItr.next()).getValue();
			if (forUI && !descriptor.isVisibleInUi())
				continue;
			if (contentService.isVisible(descriptor.getId())) {
				visibleFilters.add(descriptor);
			}
		}
		if (visibleFilters.size() == 0) {
			return NO_FILTER_DESCRIPTORS;
		}
		return visibleFilters
				.toArray(new CommonFilterDescriptor[visibleFilters.size()]);
	}

	public CommonFilterDescriptor getFilterById(String id) {
		return filters.get(id);
	}
	
	private void addCommonFilter(CommonFilterDescriptor aDescriptor) {
		filters.put(aDescriptor.getId(), aDescriptor);
	}

	private class CommonFilterDescriptorRegistry extends
			NavigatorContentRegistryReader {

		@Override
		protected boolean readElement(IConfigurationElement element) {
			if (TAG_COMMON_FILTER.equals(element.getName())) {
				addCommonFilter(new CommonFilterDescriptor(element));
				return true;
			}
			return super.readElement(element);
		}

	}

}

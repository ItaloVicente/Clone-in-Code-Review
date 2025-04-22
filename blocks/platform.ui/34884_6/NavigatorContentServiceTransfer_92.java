
package org.eclipse.ui.internal.navigator.dnd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.navigator.extensions.ExtensionSequenceNumberComparator;
import org.eclipse.ui.internal.navigator.extensions.INavigatorContentExtPtConstants;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentRegistryReader;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonDropDescriptorManager {

	private static final CommonDropDescriptorManager INSTANCE = new CommonDropDescriptorManager();

	private static final CommonDropAdapterDescriptor[] NO_DESCRIPTORS = new CommonDropAdapterDescriptor[0];

	private final Map<INavigatorContentDescriptor, List> dropDescriptors = new TreeMap<INavigatorContentDescriptor, List>(ExtensionSequenceNumberComparator.INSTANCE);

	public static CommonDropDescriptorManager getInstance() {
		return INSTANCE;
	}

	private CommonDropDescriptorManager() {
		new CommonDropAdapterRegistry().readRegistry();
	}

	public CommonDropAdapterDescriptor[] findCommonDropAdapterAssistants(Object aDropTarget, INavigatorContentService aContentService) {

		Set<CommonDropAdapterDescriptor> foundDescriptors = new LinkedHashSet<CommonDropAdapterDescriptor>();
		for (Iterator<INavigatorContentDescriptor> iter = dropDescriptors.keySet().iterator(); iter
				.hasNext();) {
			INavigatorContentDescriptor contentDescriptor = iter
					.next();
			if (aContentService.isVisible(contentDescriptor.getId())
					&& aContentService.isActive(contentDescriptor.getId())) {
				List<CommonDropAdapterDescriptor> dropDescriptors = getDropDescriptors(contentDescriptor);
				for (Iterator<CommonDropAdapterDescriptor> iterator = dropDescriptors.iterator(); iterator
						.hasNext();) {
					CommonDropAdapterDescriptor dropDescriptor = iterator
							.next();
					if (dropDescriptor.isDropElementSupported(aDropTarget)) {
						foundDescriptors.add(dropDescriptor);
					}
				}
			}
		}

		if (foundDescriptors.isEmpty()) {
			return NO_DESCRIPTORS;
		}
		return foundDescriptors
				.toArray(new CommonDropAdapterDescriptor[foundDescriptors
						.size()]);
	}

	private List<CommonDropAdapterDescriptor> getDropDescriptors(
			INavigatorContentDescriptor aContentDescriptor) {
		List<CommonDropAdapterDescriptor> descriptors = dropDescriptors.get(aContentDescriptor);
		if (descriptors != null) {
			return descriptors;
		}
		synchronized (dropDescriptors) {
			descriptors = dropDescriptors.get(aContentDescriptor);
			if (descriptors == null) {
				dropDescriptors.put(aContentDescriptor,
						(descriptors = new ArrayList<CommonDropAdapterDescriptor>()));
			}

		}
		return descriptors;
	}

	private void addCommonDropAdapter(
			INavigatorContentDescriptor aContentDescriptor,
			CommonDropAdapterDescriptor aDropDescriptor) { 
		getDropDescriptors(aContentDescriptor).add(aDropDescriptor);
	}

	private class CommonDropAdapterRegistry extends
			NavigatorContentRegistryReader implements
			INavigatorContentExtPtConstants {

		private CommonDropAdapterRegistry() {
		}

		@Override
		protected boolean readElement(IConfigurationElement element) {

			if (TAG_NAVIGATOR_CONTENT.equals(element.getName())) {

				String id = element.getAttribute(ATT_ID);
				if (id != null) {
					INavigatorContentDescriptor contentDescriptor = CONTENT_DESCRIPTOR_MANAGER
							.getContentDescriptor(id);
					if (contentDescriptor != null) {

						IConfigurationElement[] commonDropAdapters = element
								.getChildren(TAG_COMMON_DROP_ADAPTER);

						for (int i = 0; i < commonDropAdapters.length; i++) {
							addCommonDropAdapter(contentDescriptor,
									new CommonDropAdapterDescriptor(commonDropAdapters[i], contentDescriptor));
						} 
					}
				}

			}
			return super.readElement(element);
		}

	}

}

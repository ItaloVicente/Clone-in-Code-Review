
package org.eclipse.ui.internal.navigator.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.navigator.INavigatorContentService;

public class LinkHelperManager {

	private static final LinkHelperManager instance = new LinkHelperManager();

	private static final LinkHelperDescriptor[] NO_DESCRIPTORS = new LinkHelperDescriptor[0];

	private List<LinkHelperDescriptor> descriptors;

	public static LinkHelperManager getInstance() {
		return instance;
	}

	private LinkHelperManager() {
		new LinkHelperRegistry().readRegistry();
	}

	public LinkHelperDescriptor[] getLinkHelpersFor(
			Object anObject,
			INavigatorContentService aContentService) {

		List<LinkHelperDescriptor> helpersList = new ArrayList<LinkHelperDescriptor>();
		LinkHelperDescriptor descriptor = null;
		for (Iterator<LinkHelperDescriptor> itr = getDescriptors().iterator(); itr.hasNext();) {
			descriptor = itr.next();
			if (aContentService.isVisible(descriptor.getId())
					&& descriptor.isEnabledFor(anObject)) {
				helpersList.add(descriptor);
			}
		}
		if (helpersList.size() == 0) {
			return NO_DESCRIPTORS;
		}
		return helpersList
				.toArray(new LinkHelperDescriptor[helpersList.size()]);

	}

	public LinkHelperDescriptor[] getLinkHelpersFor(IEditorInput anInput,
			INavigatorContentService aContentService) {

		List<LinkHelperDescriptor> helpersList = new ArrayList<LinkHelperDescriptor>();
		LinkHelperDescriptor descriptor = null;
		for (Iterator<LinkHelperDescriptor> itr = getDescriptors().iterator(); itr.hasNext();) {
			descriptor = itr.next();
			if (aContentService.isVisible(descriptor.getId())
					&& descriptor.isEnabledFor(anInput)) {
				helpersList.add(descriptor);
			}
		}
		if (helpersList.size() == 0) {
			return NO_DESCRIPTORS;
		}
		return helpersList
				.toArray(new LinkHelperDescriptor[helpersList.size()]);

	}

	protected List<LinkHelperDescriptor> getDescriptors() {
		if (descriptors == null) {
			descriptors = new ArrayList<LinkHelperDescriptor>();
		}
		return descriptors;
	}

	private class LinkHelperRegistry extends RegistryReader implements
			ILinkHelperExtPtConstants {

		private LinkHelperRegistry() {
			super(NavigatorPlugin.PLUGIN_ID, LINK_HELPER);
		}

		@Override
		public boolean readElement(final IConfigurationElement element) {
			if (LINK_HELPER.equals(element.getName())) {
				final boolean retValue[] = new boolean[1];
				SafeRunner.run(new NavigatorSafeRunnable(element) {
					@Override
					public void run() throws Exception {
						getDescriptors().add(new LinkHelperDescriptor(element));
						retValue[0] = true;
					}
				});
				return retValue[0];
			}
			return false;
		}
	}
}

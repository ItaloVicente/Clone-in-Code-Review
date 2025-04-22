package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;

public class NavigatorContentRegistryReader extends RegistryReader implements
		INavigatorContentExtPtConstants {

	protected final NavigatorContentDescriptorManager CONTENT_DESCRIPTOR_MANAGER = NavigatorContentDescriptorManager
			.getInstance();

	protected NavigatorContentRegistryReader() {
		super(NavigatorPlugin.PLUGIN_ID, TAG_NAVIGATOR_CONTENT);
	}

	@Override
	protected boolean readElement(IConfigurationElement element) {
		String elementName = element.getName();

		return TAG_ACTION_PROVIDER.equals(elementName)
				|| TAG_NAVIGATOR_CONTENT.equals(elementName)
				|| TAG_COMMON_WIZARD.equals(elementName)
				|| TAG_COMMON_FILTER.equals(elementName);
	}
}

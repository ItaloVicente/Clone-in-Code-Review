
package org.eclipse.ui.internal.statushandlers;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

class StatusHandlerProductBindingDescriptor implements
		IPluginContribution {
	
	private static String ATT_HANDLER_ID = "handlerId"; //$NON-NLS-1$

	private String id;

	private String pluginId;

	private String productId;

	private String handlerId;

	public StatusHandlerProductBindingDescriptor(
			IConfigurationElement configElement) {
		super();
		id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		pluginId = configElement.getContributor().getName();
		productId = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_PRODUCTID);
		handlerId = configElement.getAttribute(ATT_HANDLER_ID);
	}

	@Override
	public String getLocalId() {
		return id;
	}

	@Override
	public String getPluginId() {
		return pluginId;
	}

	public String getProductId() {
		return productId;
	}

	public String getHandlerId() {
		return handlerId;
	}
}

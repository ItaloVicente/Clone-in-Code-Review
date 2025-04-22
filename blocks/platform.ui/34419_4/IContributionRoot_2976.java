
package org.eclipse.ui.menus;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public abstract class ExtensionContributionFactory extends
		AbstractContributionFactory implements IExecutableExtension {

	private String namespace;
	private String locationURI;

	public ExtensionContributionFactory() {
		super(null, null);
	}

	@Override
	public final String getLocation() {
		return locationURI;
	}

	@Override
	public final String getNamespace() {
		return namespace;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		locationURI = config
				.getAttribute(IWorkbenchRegistryConstants.TAG_LOCATION_URI);
		namespace = config.getNamespaceIdentifier();
	}
}

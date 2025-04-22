package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PerspectiveDescriptor implements IPerspectiveDescriptor,
		IPluginContribution {

	private String id;
	private String label;
	private ImageDescriptor image;
	private IConfigurationElement configElement;
	private boolean hasCustomDefinition;
	private String pluginId;
	private String originalId; // ID of ancestor that was based on a config

	public PerspectiveDescriptor(String id, String label, PerspectiveDescriptor originalDescriptor) {
		this.id = id;
		this.label = label;
		if (originalDescriptor != null) {
			this.originalId = originalDescriptor.getOriginalId();
			this.image = originalDescriptor.getImageDescriptor();
			this.pluginId = originalDescriptor.getPluginId();
			this.hasCustomDefinition = true;
		}
	}

	PerspectiveDescriptor(String id, IConfigurationElement element) {
		this.id = id;
		this.configElement = element;
	}

	public IConfigurationElement getConfigElement() {
		return configElement;
	}

	public IPerspectiveFactory createFactory() {
		try {
			return (IPerspectiveFactory) configElement
					.createExecutableExtension(IWorkbenchRegistryConstants.ATT_CLASS);
		} catch (CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription() {
		return configElement == null ? null : RegistryReader.getDescription(configElement);
	}

	@Override
	public String getId() {
		return id;
	}

	public String getOriginalId() {
		if (originalId == null) {
			originalId = getId();
		}

		return originalId;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		if (image != null)
			return image;

		if (configElement != null) {
			String icon = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
			if (icon != null) {
				image = AbstractUIPlugin.imageDescriptorFromPlugin(
						configElement.getNamespaceIdentifier(), icon);
			}
		}

		if (image == null)
			image = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE);

		return image;
	}

	@Override
	public String getLabel() {
		return configElement == null ? label : configElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

	@Override
	public String getLocalId() {
		return getId();
	}

	@Override
	public String getPluginId() {
		return configElement == null ? pluginId : configElement.getNamespaceIdentifier();
	}

	public boolean hasCustomDefinition() {
		return hasCustomDefinition;
	}

	public void setHasCustomDefinition(boolean value) {
		hasCustomDefinition = value;
	}

	public boolean isPredefined() {
		return configElement != null;
	}

	public boolean isSingleton() {
		return false;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " {id=" + getId() + "}"; //$NON-NLS-1$//$NON-NLS-2$
	}
}

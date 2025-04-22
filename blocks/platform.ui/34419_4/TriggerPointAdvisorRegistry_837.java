package org.eclipse.ui.internal.activities.ws;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.activities.ITriggerPointAdvisor;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public final class TriggerPointAdvisorDescriptor {

	private String id;

	private IConfigurationElement element;

	public TriggerPointAdvisorDescriptor(IConfigurationElement element)
			throws IllegalArgumentException {
		id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id == null
				|| RegistryReader.getClassValue(element,
						IWorkbenchRegistryConstants.ATT_CLASS) == null) {
			throw new IllegalArgumentException();
		}
		this.element = element;
	}

	public String getId() {
		return id;
	}

	public ITriggerPointAdvisor createAdvisor() throws CoreException {
		return (ITriggerPointAdvisor) element
				.createExecutableExtension(IWorkbenchRegistryConstants.ATT_CLASS);
	}
}

package org.eclipse.urischeme.internal.registration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;

public class RegistrationWindows implements IOperatingSystemRegistration {
	RegistryWriter registryWriter;

	@SuppressWarnings("javadoc")
	public RegistrationWindows() {
		registryWriter = new RegistryWriter();
	}

	public RegistrationWindows(RegistryWriter registryWriter) {
		this.registryWriter = registryWriter;
	}

	@Override
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove) {
		for (ISchemeInformation scheme : toAdd) {
			registryWriter.addScheme(scheme.getScheme(), scheme.getDescription());
		}
		for (ISchemeInformation scheme : toRemove) {
			registryWriter.removeScheme(scheme.getScheme());
		}
	}

	@Override
	public List<ISchemeInformation> getRegisteredSchemes(List<ISchemeInformation> schemes) {
		List<ISchemeInformation> registeredSchemes = new ArrayList<>();
		for (ISchemeInformation scheme : schemes) {
			if (null != registryWriter.getRegisteredHandlerPath(scheme.getScheme())) {
				registeredSchemes.add(scheme);
			}
		}
		return registeredSchemes;
	}
}

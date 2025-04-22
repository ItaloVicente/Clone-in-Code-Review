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
	public List<ISchemeInformation> getSchemesInformation(Collection<ISchemeInformation> schemes) {
		List<ISchemeInformation> registeredSchemes = new ArrayList<>();
		for (ISchemeInformation scheme : schemes) {
			String schemeName = scheme.getScheme();
			String schemeDescription = scheme.getDescription();
			String path = registryWriter.getRegisteredHandlerPath(schemeName);
			if (path == null) {
				path = "<none>"; //$NON-NLS-1$
		}
			ISchemeInformation registeredSchemeInformation = new SchemeInformation(schemeName, schemeDescription, path);
			registeredSchemes.add(registeredSchemeInformation);
	}
		return registeredSchemes;
	}
}

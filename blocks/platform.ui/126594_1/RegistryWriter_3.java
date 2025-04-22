package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
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

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<ISchemeInformation> schemes) throws IOException {
		List<ISchemeInformation> registeredSchemes = new ArrayList<>();
		for (ISchemeInformation scheme : schemes) {
			String schemeName = scheme.getScheme();
			String path = registryWriter.getRegisteredHandlerPath(schemeName);
			if (path == null) {
				path = "<none>"; //$NON-NLS-1$
			}
			ISchemeInformation registeredSchemeInformation = new SchemeInformation(schemeName, "Description", path); //$NON-NLS-1$
				registeredSchemes.add(registeredSchemeInformation);
		}
		return registeredSchemes;
	}

	@Override
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws IOException {
		for (ISchemeInformation scheme : toAdd) {
			registryWriter.addScheme(scheme.getScheme(), scheme.getDescription());
		}
		for (ISchemeInformation scheme : toRemove) {
			registryWriter.removeScheme(scheme.getScheme());
		}

	}

}

package org.eclipse.urischeme.internal.registration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
public class RegistrationWindows implements IOperatingSystemRegistration {
	IRegistryWriter registryWriter;

	public RegistrationWindows() {
		this(new RegistryWriter());
	}

	public RegistrationWindows(IRegistryWriter registryWriter) {
		this.registryWriter = registryWriter;
	}

	@Override
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws Exception {
		for (ISchemeInformation scheme : toAdd) {
			registryWriter.addScheme(scheme.getScheme());
		}
		for (ISchemeInformation scheme : toRemove) {
			registryWriter.removeScheme(scheme.getScheme());
		}
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) throws Exception {
		String launcher = System.getProperty("eclipse.launcher");//$NON-NLS-1$

		List<ISchemeInformation> schemeInformations = new ArrayList<>();

		for (Scheme scheme : schemes) {
			SchemeInformation schemeInfo = new SchemeInformation(scheme.getUriScheme(),
					scheme.getUriSchemeDescription(), null);
			String path = registryWriter.getRegisteredHandlerPath(schemeInfo.getScheme());
			if (path == null) {
				path = "<none>"; //$NON-NLS-1$
			}
			schemeInfo.setHandled(path.equals(launcher));
			schemeInfo.setHandlerLocation(path);
			schemeInformations.add(schemeInfo);

		}
		return schemeInformations;
	}
}

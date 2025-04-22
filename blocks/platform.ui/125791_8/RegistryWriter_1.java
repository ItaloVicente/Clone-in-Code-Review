package org.eclipse.urischeme.internal.registration;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove) {
		for (ISchemeInformation scheme : toAdd) {
			registryWriter.addScheme(scheme.getScheme());
		}
		for (ISchemeInformation scheme : toRemove) {
			registryWriter.removeScheme(scheme.getScheme());
		}
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) {
		String launcher = System.getProperty("eclipse.launcher");//$NON-NLS-1$

		Function<Scheme, ISchemeInformation> toSchemeInfo = new Function<Scheme, ISchemeInformation>() {
			@Override
			public ISchemeInformation apply(Scheme scheme) {
				SchemeInformation schemeInfo = new SchemeInformation(scheme.getUriScheme(),
						scheme.getUriSchemeDescription(), null);
				String path = registryWriter.getRegisteredHandlerPath(schemeInfo.getScheme());
				if (path == null) {
					path = "<none>"; //$NON-NLS-1$
				}
				schemeInfo.setHandled(path.equals(launcher));
				schemeInfo.setHandlerLocation(path);
				return schemeInfo;
			}
		};
		return schemes.stream().map(toSchemeInfo).collect(Collectors.toList());
	}
}

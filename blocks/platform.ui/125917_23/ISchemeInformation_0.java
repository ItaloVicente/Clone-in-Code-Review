package org.eclipse.urischeme;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.eclipse.urischeme.internal.registration.RegistrationLinux;
import org.eclipse.urischeme.internal.registration.RegistrationMacOsX;
public interface IOperatingSystemRegistration {

	static IOperatingSystemRegistration getInstance() {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
			return new RegistrationMacOsX();
		} else if (Platform.OS_LINUX.equals(Platform.getOS())) {
			return new RegistrationLinux();
		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
		}
		return null;
	}

	void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws Exception;

	List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) throws Exception;

}

package org.eclipse.urischeme;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.urischeme.internal.registration.RegistrationMacOsX;*/
import org.eclipse.urischeme.internal.registration.RegistrationWindows;
public interface IOperatingSystemRegistration {

	static IOperatingSystemRegistration getInstance() {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
		} else if (Platform.OS_LINUX.equals(Platform.getOS())) {
		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return new RegistrationWindows();
		}
		return null;
	}

	void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws IOException;

	List<ISchemeInformation> getRegisteredSchemes(List<ISchemeInformation> schemes) throws IOException;

}

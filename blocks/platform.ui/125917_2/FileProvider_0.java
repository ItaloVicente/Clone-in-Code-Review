package org.eclipse.urischeme;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.urischeme.internal.registration.RegistrationLinux;
import org.eclipse.urischeme.internal.registration.RegistrationMacOsX;
import org.eclipse.urischeme.internal.registration.Scheme;

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

	void handleSchemes(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove) throws IOException;

}

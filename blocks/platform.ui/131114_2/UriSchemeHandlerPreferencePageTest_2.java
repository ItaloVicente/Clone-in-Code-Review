package org.eclipse.urischeme;

import org.eclipse.urischeme.internal.UriSchemeExtensionReader;

public class UriSchemeFactory {

	public static IUriSchemeExtensionReader extensionReader = UriSchemeExtensionReader.getInstance();
	public static IOperatingSystemRegistration operatingSystemRegistration = IOperatingSystemRegistration.getInstance();

}

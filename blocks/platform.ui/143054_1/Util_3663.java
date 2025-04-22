package org.eclipse.urischeme.internal.registration;

import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("javadoc")
public class Util {
	public static void assertUriSchemeIsLegal(String uriScheme) {
		try {
			new URI(uriScheme, "foo", ""); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("'Scheme' does not conform to RFC 2396"); //$NON-NLS-1$
		}
	}
}

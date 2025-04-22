package org.eclipse.urischeme.internal.registration;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.eclipse.urischeme.ISchemeInformation;

@SuppressWarnings("javadoc")
public class Util {
	public static void assertUriSchemeIsLegal(String uriScheme) {
		try {
			new URI(uriScheme, "foo", ""); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("'Scheme' must only contain letters"); //$NON-NLS-1$
		}
	}

	public static List<String> convertSchemes(Collection<ISchemeInformation> schemes) {
		return schemes.stream().map(s -> s.getScheme()).collect(toList());
	}

	public static List<ISchemeInformation> filter(Collection<ISchemeInformation> schemes, Collection<String> toFilter) {
		return schemes.stream().filter(s -> toFilter.contains(s.getScheme())).collect(toList());
	}
}

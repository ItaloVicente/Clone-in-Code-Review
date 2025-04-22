package org.eclipse.urischeme;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.urischeme.internal.UriSchemeExtensionReader;

public interface IUriSchemeExtensionReader {

	static IUriSchemeExtensionReader newInstance() {
		return new UriSchemeExtensionReader();
	}

	Collection<IScheme> getSchemes();

	IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException;

}

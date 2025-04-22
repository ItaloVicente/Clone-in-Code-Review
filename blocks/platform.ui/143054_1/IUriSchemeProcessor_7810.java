package org.eclipse.urischeme;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.urischeme.internal.UriSchemeProcessor;

public interface IUriSchemeProcessor {
	IUriSchemeProcessor INSTANCE = new UriSchemeProcessor();

	void handleUri(String uriScheme, String uri) throws CoreException;
}

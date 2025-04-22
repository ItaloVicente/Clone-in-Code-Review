package org.eclipse.urischeme.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.urischeme.IUriSchemeExtensionReader;
import org.eclipse.urischeme.IUriSchemeHandler;
import org.eclipse.urischeme.IUriSchemeProcessor;

public class UriSchemeProcessor implements IUriSchemeProcessor {

	private Map<String, IUriSchemeHandler> createdHandlers = new HashMap<>();
	IUriSchemeExtensionReader reader = IUriSchemeExtensionReader.newInstance();

	@Override
	public void handleUri(String uriScheme, String uri) throws CoreException {
		IUriSchemeHandler handler = null;

		if (createdHandlers.containsKey(uriScheme)) {
			handler = createdHandlers.get(uriScheme);
		} else {
			handler = reader.getHandlerFromExtensionPoint(uriScheme);
			createdHandlers.put(uriScheme, handler);
		}
		if (handler != null) {
			handler.handle(uri);
		}
	}

}

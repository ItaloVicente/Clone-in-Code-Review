package org.eclipse.urischeme.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.urischeme.IUriSchemeHandler;
import org.eclipse.urischeme.IUriSchemeProcessor;

public class UriSchemeProcessor implements IUriSchemeProcessor {

	private static final String PLUGIN_ID = "org.eclipse.urischeme"; //$NON-NLS-1$

	private static final String EXT_POINT_ID_URI_SCHEME_HANDLERS = "org.eclipse.urischeme.uriSchemeHandlers"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_URI_SCHEME = "uriScheme"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	private IConfigurationElement[] configurationElements = null;
	private Map<String, IUriSchemeHandler> createdHandlers = new HashMap<>();

	public void handleUri(String uriScheme, String uri) throws CoreException {
		IUriSchemeHandler handler = null;

		if (createdHandlers.containsKey(uriScheme)) {
			handler = createdHandlers.get(uriScheme);
		} else {
			handler = getHandlerFromExtensionPoint(uriScheme);
			createdHandlers.put(uriScheme, handler);
		}
		if (handler != null) {
			handler.handle(uri);
		}
	}

	private IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException {
		IConfigurationElement[] elements = getOrReadConfigurationElements();

		for (IConfigurationElement element : elements) {
			if (uriScheme.equals(element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME))) {
				return createExecutableSchemeHandler(element);
			}
		}
		return null;
	}

	private IConfigurationElement[] getOrReadConfigurationElements() {
		if (this.configurationElements == null) {
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			this.configurationElements = registry.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		}
		return configurationElements;
	}

	private IUriSchemeHandler createExecutableSchemeHandler(IConfigurationElement element) throws CoreException {
		Object executableExtension = element.createExecutableExtension(EXT_POINT_ATTRIBUTE_CLASS);
		if (executableExtension instanceof IUriSchemeHandler) {
			return (IUriSchemeHandler) executableExtension;
		} else {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID,
					"Registered class has wrong type: " + executableExtension.getClass())); //$NON-NLS-1$
		}
	}
}

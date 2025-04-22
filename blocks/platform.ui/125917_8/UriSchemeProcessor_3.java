package org.eclipse.urischeme.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.Status;
import org.eclipse.urischeme.IUriSchemeExtensionReader;
import org.eclipse.urischeme.IUriSchemeHandler;

public class UriSchemeExtensionReader implements IUriSchemeExtensionReader {

	private static final String PLUGIN_ID = "org.eclipse.urischeme"; //$NON-NLS-1$
	private static final String EXT_POINT_ID_URI_SCHEME_HANDLERS = "org.eclipse.urischeme.uriSchemeHandlers"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_URI_SCHEME = "uriScheme"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_URI_SCHEME_DESCRIPTION = "uriSchemeDescription"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	private IConfigurationElement[] configurationElements = null;

	private static UriSchemeExtensionReader INSTANCE = new UriSchemeExtensionReader();

	public static UriSchemeExtensionReader getInstance() {
		return INSTANCE;
	}

	private UriSchemeExtensionReader() {
	}

	@Override
	public Collection<Scheme> getSchemes() {
		IConfigurationElement[] elements = getOrReadConfigurationElements();
		Collection<Scheme> schemes = new ArrayList<>();
		for (IConfigurationElement element : elements) {
			String schemeName = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME);
			String schemeDescription = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME_DESCRIPTION);
			Scheme scheme = new Scheme(schemeName, schemeDescription);
			schemes.add(scheme);
		}
		return schemes;
	}

	public IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException {
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
			IExtensionRegistry registry = RegistryFactory.getRegistry();
			this.configurationElements = registry.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		}
		return configurationElements;
	}

	private IUriSchemeHandler createExecutableSchemeHandler(IConfigurationElement element) throws CoreException {
		Object executableExtension = element.createExecutableExtension(EXT_POINT_ATTRIBUTE_CLASS);
		if (executableExtension instanceof IUriSchemeHandler) {
			return (IUriSchemeHandler) executableExtension;
		}
		throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID,
				"Registered class has wrong type: " + executableExtension.getClass())); //$NON-NLS-1$
	}

}

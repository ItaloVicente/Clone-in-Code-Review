package org.eclipse.ui.internal.navigator.extensions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;

public abstract class RegistryReader {

	protected static final String TAG_DESCRIPTION = "description"; //$NON-NLS-1$

	private boolean isInitialized;
	private final String extensionPointId;
	private final String pluginId;
	private final IExtensionRegistry registry;

	protected RegistryReader(String aPluginId, String anExtensionPoint) {
		this.registry = Platform.getExtensionRegistry();
		this.pluginId = aPluginId;
		this.extensionPointId = anExtensionPoint;
	}

	protected String getDescription(IConfigurationElement config) {
		IConfigurationElement[] children = config.getChildren(TAG_DESCRIPTION);
		if (children.length >= 1) {
			return children[0].getValue();
		}
		return "";//$NON-NLS-1$
	}

	protected static void logError(IConfigurationElement element, String text) {
		IExtension extension = element.getDeclaringExtension();
		StringBuffer buf = new StringBuffer();
		buf.append("Plugin " + extension.getNamespaceIdentifier() + ", extension " + extension.getExtensionPointUniqueIdentifier());//$NON-NLS-2$//$NON-NLS-1$
		buf.append("\n" + text);//$NON-NLS-1$
		NavigatorPlugin.logError(0, buf.toString(), null);
	}

	protected static void logMissingAttribute(IConfigurationElement element, String attributeName) {
		logError(element, "Required attribute '" + attributeName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected static void logMissingElement(IConfigurationElement element, String elementName) {
		logError(element, "Required sub element '" + elementName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected static void logUnknownElement(IConfigurationElement element) {
		logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
	}

	protected IExtension[] orderExtensions(IExtension[] extensions) {
		IExtension[] sortedExtension = new IExtension[extensions.length];
		System.arraycopy(extensions, 0, sortedExtension, 0, extensions.length);
		Comparator comparer = new Comparator() {
			@Override
			public int compare(Object arg0, Object arg1) {
				String s1 = ((IExtension) arg0).getNamespaceIdentifier();
				String s2 = ((IExtension) arg1).getNamespaceIdentifier();
				return s1.compareToIgnoreCase(s2);
			}
		};
		Collections.sort(Arrays.asList(sortedExtension), comparer);
		return sortedExtension;
	}

	protected abstract boolean readElement(IConfigurationElement element);

	protected void readElementChildren(IConfigurationElement element) {
		readElements(element.getChildren());
	}

	protected void readElements(IConfigurationElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			if (!readElement(elements[i])) {
				logUnknownElement(elements[i]);
			}
		}
	}

	protected void readExtension(IExtension extension) {
		readElements(extension.getConfigurationElements());
	}

	public void readRegistry() {
		if (isInitialized) {
			return;
		}
		synchronized (this) {
			if (!isInitialized) {
				IExtensionPoint point = registry.getExtensionPoint(pluginId, extensionPointId);
				if (point == null) {
					return;
				}
				IExtension[] extensions = point.getExtensions();
				extensions = orderExtensions(extensions);
				for (int i = 0; i < extensions.length; i++) {
					readExtension(extensions[i]);
				}
				isInitialized = true;
			}

		}
	}
}

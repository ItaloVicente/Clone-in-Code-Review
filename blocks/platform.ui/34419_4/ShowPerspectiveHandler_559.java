
package org.eclipse.ui.handlers;

import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.commands.ToggleState;

public final class RegistryToggleState extends ToggleState implements
		IExecutableExtension {
	
	public final static String STATE_ID = "org.eclipse.ui.commands.toggleState";  //$NON-NLS-1$

	private final void readDefault(final String defaultString) {
		if ("true".equalsIgnoreCase(defaultString)) { //$NON-NLS-1$
			setValue(Boolean.TRUE);
		}
	}

	private final void readPersisted(final String persistedString) {
		if ("false".equalsIgnoreCase(persistedString)) { //$NON-NLS-1$
			setShouldPersist(false);
		}else {
			setShouldPersist(true);
		}
	}

	@Override
	public final void setInitializationData(
			final IConfigurationElement configurationElement,
			final String propertyName, final Object data) {
		if (data instanceof String) {
			readDefault((String) data);
			setShouldPersist(true);

		} else if (data instanceof Hashtable) {
			final Hashtable parameters = (Hashtable) data;
			final Object defaultObject = parameters.get("default"); //$NON-NLS-1$
			if (defaultObject instanceof String) {
				readDefault((String) defaultObject);
			}

			final Object persistedObject = parameters.get("persisted"); //$NON-NLS-1$
			if (persistedObject instanceof String) {
				readPersisted((String) persistedObject);
			}

		} else {
			setShouldPersist(true);
			
		}
	}
}

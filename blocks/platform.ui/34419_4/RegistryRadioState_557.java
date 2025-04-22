
package org.eclipse.ui.handlers;

import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.commands.PersistentState;
import org.eclipse.jface.preference.IPreferenceStore;

public final class RadioState extends PersistentState implements
		IExecutableExtension {

	public final static String STATE_ID = "org.eclipse.ui.commands.radioState"; //$NON-NLS-1$

	public final static String PARAMETER_ID = "org.eclipse.ui.commands.radioStateParameter"; //$NON-NLS-1$

	public RadioState() {
		setShouldPersist(true);
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {

		boolean shouldPersist = true; // persist by default
		if (data instanceof String) {
			setValue(data);
		} else if (data instanceof Hashtable) {
			final Hashtable parameters = (Hashtable) data;
			final Object defaultObject = parameters.get("default"); //$NON-NLS-1$
			if (defaultObject instanceof String) {
				setValue(defaultObject);
			}

			final Object persistedObject = parameters.get("persisted"); //$NON-NLS-1$
			if (persistedObject instanceof String
					&& "false".equalsIgnoreCase(((String) persistedObject))) //$NON-NLS-1$
				shouldPersist = false;
		}
		setShouldPersist(shouldPersist);

	}

	@Override
	public void load(IPreferenceStore store, String preferenceKey) {
		if (!shouldPersist())
			return;
		final String value = store.getString(preferenceKey);
		if (value.length() != 0)
			setValue(value);
	}

	@Override
	public void save(IPreferenceStore store, String preferenceKey) {
		if (!shouldPersist())
			return;
		final Object value = getValue();
		if (value instanceof String) {
			store.setValue(preferenceKey, (String) value);
		}
	}

	@Override
	public void setValue(Object value) {
		if (!(value instanceof String))
			return; // we set only String values
		super.setValue(value);
	}

}

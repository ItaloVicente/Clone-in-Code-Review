package org.eclipse.ui.preferences;

import java.io.IOException;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.osgi.service.prefs.BackingStoreException;

public class ScopedPreferenceStore extends EventManager implements
		IPreferenceStore, IPersistentPreferenceStore {

	private IScopeContext storeContext;

	private IScopeContext[] searchContexts;

	protected boolean silentRunning = false;

	IEclipsePreferences.IPreferenceChangeListener preferencesListener;

	private IScopeContext defaultContext = new DefaultScope();

	String nodeQualifier;

	String defaultQualifier;

	private boolean dirty;

	public ScopedPreferenceStore(IScopeContext context, String qualifier,
			String defaultQualifierPath) {
		this(context, qualifier);
		this.defaultQualifier = defaultQualifierPath;
	}

	public ScopedPreferenceStore(IScopeContext context, String qualifier) {
		storeContext = context;
		this.nodeQualifier = qualifier;
		this.defaultQualifier = qualifier;

		((IEclipsePreferences) getStorePreferences().parent())
				.addNodeChangeListener(getNodeChangeListener());
	}

	private INodeChangeListener getNodeChangeListener() {
		return new IEclipsePreferences.INodeChangeListener() {
			@Override
			public void added(NodeChangeEvent event) {
				if (nodeQualifier.equals(event.getChild().name())
						&& isListenerAttached()) {
					getStorePreferences().addPreferenceChangeListener(
							preferencesListener);
				}
			}

			@Override
			public void removed(NodeChangeEvent event) {
			}
		};
	}

	private void initializePreferencesListener() {
		if (preferencesListener == null) {
			preferencesListener = new IEclipsePreferences.IPreferenceChangeListener() {
				@Override
				public void preferenceChange(PreferenceChangeEvent event) {

					if (silentRunning) {
						return;
					}

					Object oldValue = event.getOldValue();
					Object newValue = event.getNewValue();
					String key = event.getKey();
					if (newValue == null) {
						newValue = getDefault(key, oldValue);
					} else if (oldValue == null) {
						oldValue = getDefault(key, newValue);
					}
					firePropertyChangeEvent(event.getKey(), oldValue, newValue);
				}
			};
			getStorePreferences().addPreferenceChangeListener(
					preferencesListener);
		}

	}

	Object getDefault(String key, Object obj) {
		IEclipsePreferences defaults = getDefaultPreferences();
		if (obj instanceof String) {
			return defaults.get(key, STRING_DEFAULT_DEFAULT);
		} else if (obj instanceof Integer) {
			return new Integer(defaults.getInt(key, INT_DEFAULT_DEFAULT));
		} else if (obj instanceof Double) {
			return new Double(defaults.getDouble(key, DOUBLE_DEFAULT_DEFAULT));
		} else if (obj instanceof Float) {
			return new Float(defaults.getFloat(key, FLOAT_DEFAULT_DEFAULT));
		} else if (obj instanceof Long) {
			return new Long(defaults.getLong(key, LONG_DEFAULT_DEFAULT));
		} else if (obj instanceof Boolean) {
			return defaults.getBoolean(key, BOOLEAN_DEFAULT_DEFAULT) ? Boolean.TRUE
					: Boolean.FALSE;
		} else {
			return null;
		}
	}

	IEclipsePreferences getStorePreferences() {
		return storeContext.getNode(nodeQualifier);
	}

	private IEclipsePreferences getDefaultPreferences() {
		return defaultContext.getNode(defaultQualifier);
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		initializePreferencesListener();// Create the preferences listener if it
		addListenerObject(listener);
	}

	public IEclipsePreferences[] getPreferenceNodes(boolean includeDefault) {
		if (searchContexts == null) {
			if (includeDefault) {
				return new IEclipsePreferences[] { getStorePreferences(),
						getDefaultPreferences() };
			}
			return new IEclipsePreferences[] { getStorePreferences() };
		}
		int length = searchContexts.length;
		if (includeDefault) {
			length++;
		}
		IEclipsePreferences[] preferences = new IEclipsePreferences[length];
		for (int i = 0; i < searchContexts.length; i++) {
			preferences[i] = searchContexts[i].getNode(nodeQualifier);
		}
		if (includeDefault) {
			preferences[length - 1] = getDefaultPreferences();
		}
		return preferences;
	}

	public void setSearchContexts(IScopeContext[] scopes) {
		this.searchContexts = scopes;
		if (scopes == null) {
			return;
		}

		for (int i = 0; i < scopes.length; i++) {
			if (scopes[i].equals(defaultContext)) {
				Assert
						.isTrue(
								false,
								WorkbenchMessages.ScopedPreferenceStore_DefaultAddedError);
			}
		}
	}

	@Override
	public boolean contains(String name) {
		if (name == null) {
			return false;
		}
		return (Platform.getPreferencesService().get(name, null,
				getPreferenceNodes(true))) != null;
	}

	@Override
	public void firePropertyChangeEvent(String name, Object oldValue,
			Object newValue) {
		final Object[] list = getListeners();
		if (list.length == 0) {
			return;
		}
		final PropertyChangeEvent event = new PropertyChangeEvent(this, name,
				oldValue, newValue);
		for (int i = 0; i < list.length; i++) {
			final IPropertyChangeListener listener = (IPropertyChangeListener) list[i];
			SafeRunner.run(new SafeRunnable(JFaceResources
					.getString("PreferenceStore.changeError")) { //$NON-NLS-1$
						@Override
						public void run() {
							listener.propertyChange(event);
						}
					});
		}
	}

	@Override
	public boolean getBoolean(String name) {
		String value = internalGet(name);
		return value == null ? BOOLEAN_DEFAULT_DEFAULT : Boolean.valueOf(value)
				.booleanValue();
	}

	@Override
	public boolean getDefaultBoolean(String name) {
		return getDefaultPreferences()
				.getBoolean(name, BOOLEAN_DEFAULT_DEFAULT);
	}

	@Override
	public double getDefaultDouble(String name) {
		return getDefaultPreferences().getDouble(name, DOUBLE_DEFAULT_DEFAULT);
	}

	@Override
	public float getDefaultFloat(String name) {
		return getDefaultPreferences().getFloat(name, FLOAT_DEFAULT_DEFAULT);
	}

	@Override
	public int getDefaultInt(String name) {
		return getDefaultPreferences().getInt(name, INT_DEFAULT_DEFAULT);
	}

	@Override
	public long getDefaultLong(String name) {
		return getDefaultPreferences().getLong(name, LONG_DEFAULT_DEFAULT);
	}

	@Override
	public String getDefaultString(String name) {
		return getDefaultPreferences().get(name, STRING_DEFAULT_DEFAULT);
	}

	@Override
	public double getDouble(String name) {
		String value = internalGet(name);
		if (value == null) {
			return DOUBLE_DEFAULT_DEFAULT;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return DOUBLE_DEFAULT_DEFAULT;
		}
	}

	private String internalGet(String key) {
		return Platform.getPreferencesService().get(key, null,
				getPreferenceNodes(true));
	}

	@Override
	public float getFloat(String name) {
		String value = internalGet(name);
		if (value == null) {
			return FLOAT_DEFAULT_DEFAULT;
		}
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return FLOAT_DEFAULT_DEFAULT;
		}
	}

	@Override
	public int getInt(String name) {
		String value = internalGet(name);
		if (value == null) {
			return INT_DEFAULT_DEFAULT;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return INT_DEFAULT_DEFAULT;
		}
	}

	@Override
	public long getLong(String name) {
		String value = internalGet(name);
		if (value == null) {
			return LONG_DEFAULT_DEFAULT;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return LONG_DEFAULT_DEFAULT;
		}
	}

	@Override
	public String getString(String name) {
		String value = internalGet(name);
		return value == null ? STRING_DEFAULT_DEFAULT : value;
	}

	@Override
	public boolean isDefault(String name) {
		if (name == null) {
			return false;
		}
		return (Platform.getPreferencesService().get(name, null,
				getPreferenceNodes(false))) == null;
	}

	@Override
	public boolean needsSaving() {
		return dirty;
	}

	@Override
	public void putValue(String name, String value) {
		try {
			silentRunning = true;
			getStorePreferences().put(name, value);
		} finally {
			silentRunning = false;
			dirty = true;
		}
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		removeListenerObject(listener);
		if (!isListenerAttached()) {
			disposePreferenceStoreListener();
		}
	}

	@Override
	public void setDefault(String name, double value) {
		getDefaultPreferences().putDouble(name, value);
	}

	@Override
	public void setDefault(String name, float value) {
		getDefaultPreferences().putFloat(name, value);
	}

	@Override
	public void setDefault(String name, int value) {
		getDefaultPreferences().putInt(name, value);
	}

	@Override
	public void setDefault(String name, long value) {
		getDefaultPreferences().putLong(name, value);
	}

	@Override
	public void setDefault(String name, String defaultObject) {
		getDefaultPreferences().put(name, defaultObject);
	}

	@Override
	public void setDefault(String name, boolean value) {
		getDefaultPreferences().putBoolean(name, value);
	}

	@Override
	public void setToDefault(String name) {

		String oldValue = getString(name);
		String defaultValue = getDefaultString(name);
		try {
			silentRunning = true;// Turn off updates from the store
			getStorePreferences().remove(name);
			if (oldValue != defaultValue){
				dirty = true;
				firePropertyChangeEvent(name, oldValue, defaultValue);
			}
				
		} finally {
			silentRunning = false;// Restart listening to preferences
		}

	}

	@Override
	public void setValue(String name, double value) {
		double oldValue = getDouble(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultDouble(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putDouble(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, new Double(oldValue), new Double(
					value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	@Override
	public void setValue(String name, float value) {
		float oldValue = getFloat(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultFloat(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putFloat(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, new Float(oldValue), new Float(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	@Override
	public void setValue(String name, int value) {
		int oldValue = getInt(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultInt(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putInt(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, new Integer(oldValue), new Integer(
					value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	@Override
	public void setValue(String name, long value) {
		long oldValue = getLong(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultLong(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putLong(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, new Long(oldValue), new Long(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	@Override
	public void setValue(String name, String value) {
		if (getDefaultString(name).equals(value)) {
			getStorePreferences().remove(name);
		} else {
			getStorePreferences().put(name, value);
		}
		dirty = true;
	}

	@Override
	public void setValue(String name, boolean value) {
		boolean oldValue = getBoolean(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultBoolean(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putBoolean(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, oldValue ? Boolean.TRUE
					: Boolean.FALSE, value ? Boolean.TRUE : Boolean.FALSE);
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	@Override
	public void save() throws IOException {
		try {
			getStorePreferences().flush();
			dirty = false;
		} catch (BackingStoreException e) {
			throw new IOException(e.getMessage());
		}

	}

	private void disposePreferenceStoreListener() {

		IEclipsePreferences root = (IEclipsePreferences) Platform
				.getPreferencesService().getRootNode().node(
						Plugin.PLUGIN_PREFERENCE_SCOPE);
		try {
			if (!(root.nodeExists(nodeQualifier))) {
				return;
			}
		} catch (BackingStoreException e) {
			return;// No need to report here as the node won't have the
		}

		IEclipsePreferences preferences = getStorePreferences();
		if (preferences == null) {
			return;
		}
		if (preferencesListener != null) {
			preferences.removePreferenceChangeListener(preferencesListener);
			preferencesListener = null;
		}
	}

}


package org.eclipse.ui.internal.commands;

import org.eclipse.core.commands.IStateListener;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.commands.PersistentState;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.WorkbenchPlugin;

public final class CommandStateProxy extends PersistentState {

	private IConfigurationElement configurationElement;

	private String preferenceKey;

	private IPreferenceStore preferenceStore;

	private State state = null;

	private final String stateAttributeName;

	public CommandStateProxy(final IConfigurationElement configurationElement,
			final String stateAttributeName,
			final IPreferenceStore preferenceStore, final String preferenceKey) {

		if (configurationElement == null) {
			throw new NullPointerException(
					"The configuration element backing a state proxy cannot be null"); //$NON-NLS-1$
		}

		if (stateAttributeName == null) {
			throw new NullPointerException(
					"The attribute containing the state class must be known"); //$NON-NLS-1$
		}

		this.configurationElement = configurationElement;
		this.stateAttributeName = stateAttributeName;
		this.preferenceKey = preferenceKey;
		this.preferenceStore = preferenceStore;
	}

	@Override
	public final void addListener(final IStateListener listener) {
		if (state == null) {
			addListenerObject(listener);
		} else {
			state.addListener(listener);
		}
	}

	@Override
	public final void dispose() {
		if (state != null) {
			state.dispose();
			if (state instanceof PersistentState) {
				final PersistentState persistableState = (PersistentState) state;
				if (persistableState.shouldPersist() && preferenceStore != null
						&& preferenceKey != null) {
					persistableState.save(preferenceStore, preferenceKey);
				}
			}
		}
	}

	@Override
	public final Object getValue() {
		if (loadState()) {
			return state.getValue();
		}

		return null;
	}

	@Override
	public final void load(final IPreferenceStore store,
			final String preferenceKey) {
		if (loadState() && state instanceof PersistentState) {
			final PersistentState persistableState = (PersistentState) state;
			if (persistableState.shouldPersist() && preferenceStore != null
					&& preferenceKey != null) {
				persistableState.load(preferenceStore, preferenceKey);
			}
		}
	}

	private final boolean loadState() {
		return loadState(false);
	}

	private final boolean loadState(final boolean readPersistence) {
		if (state == null) {
			try {
				state = (State) configurationElement
						.createExecutableExtension(stateAttributeName);
				state.setId(getId());
				configurationElement = null;

				if (readPersistence && state instanceof PersistentState) {
					final PersistentState persistentState = (PersistentState) state;
					persistentState.setShouldPersist(true);
				}
				load(preferenceStore, preferenceKey);

				final Object[] listenerArray = getListeners();
				for (int i = 0; i < listenerArray.length; i++) {
					state.addListener((IStateListener) listenerArray[i]);
				}
				clearListeners();

				return true;

			} catch (final ClassCastException e) {
				final String message = "The proxied state was the wrong class"; //$NON-NLS-1$
				final IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;

			} catch (final CoreException e) {
				final String message = "The proxied state for '" + configurationElement.getAttribute(stateAttributeName) //$NON-NLS-1$
						+ "' could not be loaded"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;
			}
		}

		return true;
	}

	@Override
	public final void removeListener(final IStateListener listener) {
		if (state == null) {
			removeListenerObject(listener);
		} else {
			state.removeListener(listener);
		}
	}

	@Override
	public final void save(final IPreferenceStore store,
			final String preferenceKey) {
		if (loadState() && state instanceof PersistentState) {
			((PersistentState) state).save(store, preferenceKey);
		}
	}

	@Override
	public final void setId(final String id) {
		super.setId(id);
		if (state != null) {
			state.setId(id);
		}
	}

	@Override
	public final void setShouldPersist(final boolean persisted) {
		if (loadState(persisted) && state instanceof PersistentState) {
			((PersistentState) state).setShouldPersist(persisted);
		}
	}

	@Override
	public final void setValue(final Object value) {
		if (loadState()) {
			state.setValue(value);
		}
	}

	@Override
	public final boolean shouldPersist() {
		if (loadState() && state instanceof PersistentState) {
			return ((PersistentState) state).shouldPersist();
		}

		return false;
	}

	@Override
	public final String toString() {
		if (state == null) {
			return configurationElement.getAttribute(stateAttributeName);
		}

		return state.toString();
	}
}

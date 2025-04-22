
package org.eclipse.ui.internal.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;

public abstract class PreferencePersistence extends RegistryPersistence {

	protected static final void addElementToIndexedArray(
			final IMemento elementToAdd, final IMemento[][] indexedArray,
			final int index, final int currentCount) {
		final IMemento[] elements;
		if (currentCount == 0) {
			elements = new IMemento[1];
			indexedArray[index] = elements;
		} else {
			if (currentCount >= indexedArray[index].length) {
				final IMemento[] copy = new IMemento[indexedArray[index].length * 2];
				System.arraycopy(indexedArray[index], 0, copy, 0, currentCount);
				elements = copy;
				indexedArray[index] = elements;
			} else {
				elements = indexedArray[index];
			}
		}
		elements[currentCount] = elementToAdd;
	}

	protected static final void addWarning(final List warningsToLog,
			final String message) {
		addWarning(warningsToLog, message, null, null, null);
	}

	protected static final void addWarning(final List warningsToLog,
			final String message, final String id) {
		addWarning(warningsToLog, message, id, null, null);
	}

	protected static final void addWarning(final List warningsToLog,
			final String message, final String id,
			final String extraAttributeName, final String extraAttributeValue) {
		String statusMessage = message;
		if (id != null) {
			statusMessage = statusMessage + ": id='" + id + '\''; //$NON-NLS-1$
		}
		if (extraAttributeName != null) {
			if (id != null) {
				statusMessage = statusMessage + ',';
			} else {
				statusMessage = statusMessage + ':';
			}
			statusMessage = statusMessage + ' ' + extraAttributeName + "='" //$NON-NLS-1$
					+ extraAttributeValue + '\'';
		}

		final IStatus status = new Status(IStatus.WARNING,
				WorkbenchPlugin.PI_WORKBENCH, 0, statusMessage, null);
		warningsToLog.add(status);
	}

	protected static final boolean readBoolean(final IMemento memento,
			final String attribute, final boolean defaultValue) {
		final String value = memento.getString(attribute);
		if (value == null) {
			return defaultValue;
		}

		if (defaultValue) {
			return !value.equalsIgnoreCase("false"); //$NON-NLS-1$
		}

		return !value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	protected static final String readOptional(final IMemento memento,
			final String attribute) {
		String value = memento.getString(attribute);
		if ((value != null) && (value.length() == 0)) {
			value = null;
		}

		return value;
	}

	protected static final ParameterizedCommand readParameterizedCommand(
			final IMemento memento, final ICommandService commandService,
			final List warningsToLog, final String message, final String id) {
		final String commandId = readRequired(memento, ATT_COMMAND_ID,
				warningsToLog, message, id);
		if (commandId == null) {
			return null;
		}

		final Command command = commandService.getCommand(commandId);
		final ParameterizedCommand parameterizedCommand = readParameters(
				memento, warningsToLog, command);

		return parameterizedCommand;
	}

	protected static final ParameterizedCommand readParameters(
			final IMemento memento, final List warningsToLog,
			final Command command) {
		final IMemento[] parameterMementos = memento
				.getChildren(TAG_PARAMETER);
		if ((parameterMementos == null) || (parameterMementos.length == 0)) {
			return new ParameterizedCommand(command, null);
		}

		final Collection parameters = new ArrayList();
		for (int i = 0; i < parameterMementos.length; i++) {
			final IMemento parameterMemento = parameterMementos[i];

			final String id = parameterMemento.getString(ATT_ID);
			if ((id == null) || (id.length() == 0)) {
				addWarning(warningsToLog, "Parameters need a name"); //$NON-NLS-1$
				continue;
			}

			IParameter parameter = null;
			try {
				final IParameter[] commandParameters = command.getParameters();
				if (parameters != null) {
					for (int j = 0; j < commandParameters.length; j++) {
						final IParameter currentParameter = commandParameters[j];
						if (Util.equals(currentParameter.getId(), id)) {
							parameter = currentParameter;
							break;
						}
					}

				}
			} catch (final NotDefinedException e) {
			}
			if (parameter == null) {
				addWarning(warningsToLog,
						"Could not find a matching parameter", id); //$NON-NLS-1$
				continue;
			}

			final String value = parameterMemento.getString(ATT_VALUE);
			if ((value == null) || (value.length() == 0)) {
				addWarning(warningsToLog, "Parameters need a value", id); //$NON-NLS-1$
				continue;
			}

			parameters.add(new Parameterization(parameter, value));
		}

		if (parameters.isEmpty()) {
			return new ParameterizedCommand(command, null);
		}

		return new ParameterizedCommand(command,
				(Parameterization[]) parameters
						.toArray(new Parameterization[parameters.size()]));
	}

	protected static final String readRequired(final IMemento memento,
			final String attribute, final List warningsToLog,
			final String message) {
		return readRequired(memento, attribute, warningsToLog, message, null);
	}

	protected static final String readRequired(final IMemento memento,
			final String attribute, final List warningsToLog,
			final String message, final String id) {
		final String value = memento.getString(attribute);
		if ((value == null) || (value.length() == 0)) {
			addWarning(warningsToLog, message, id);
			return null;
		}

		return value;
	}

	protected boolean preferenceListenerAttached = false;

	private final IPropertyChangeListener preferenceChangeListener;

	@Override
	public final void dispose() {
		super.dispose();

		final IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		store.removePropertyChangeListener(preferenceChangeListener);
	}

	protected abstract boolean isChangeImportant(final PropertyChangeEvent event);

	@Override
	protected void read() {
		super.read();

		if (!preferenceListenerAttached) {
			final IPreferenceStore store = WorkbenchPlugin.getDefault()
					.getPreferenceStore();
			store.addPropertyChangeListener(preferenceChangeListener);
		}
	}

	protected PreferencePersistence() {
		super();

		preferenceChangeListener = new IPropertyChangeListener() {
			@Override
			public final void propertyChange(final PropertyChangeEvent event) {
				if (isChangeImportant(event)) {
					read();
				}
			}
		};
	}
}

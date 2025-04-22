
package org.eclipse.ui.internal.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.expressions.ElementHandler;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.services.IDisposable;

public abstract class RegistryPersistence implements IDisposable,
		IWorkbenchRegistryConstants {

	protected static final Expression ERROR_EXPRESSION = new Expression() {
		@Override
		public final EvaluationResult evaluate(final IEvaluationContext context) {
			return null;
		}
	};

	protected static final void addElementToIndexedArray(
			final IConfigurationElement elementToAdd,
			final IConfigurationElement[][] indexedArray, final int index,
			final int currentCount) {
		final IConfigurationElement[] elements;
		if (currentCount == 0) {
			elements = new IConfigurationElement[1];
			indexedArray[index] = elements;
		} else {
			if (currentCount >= indexedArray[index].length) {
				final IConfigurationElement[] copy = new IConfigurationElement[indexedArray[index].length * 2];
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
			final String message, final IConfigurationElement element) {
		addWarning(warningsToLog, message, element, null, null, null);
	}

	protected static final void addWarning(final List warningsToLog,
			final String message, final IConfigurationElement element,
			final String id) {
		addWarning(warningsToLog, message, element, id, null, null);
	}

	protected static final void addWarning(final List warningsToLog,
			final String message, final IConfigurationElement element,
			final String id, final String extraAttributeName,
			final String extraAttributeValue) {
		String statusMessage = message;
		if (element != null) {
			statusMessage = statusMessage
					+ ": plug-in='" + element.getNamespace() + '\''; //$NON-NLS-1$
		}
		if (id != null) {
			if (element != null) {
				statusMessage = statusMessage + ',';
			} else {
				statusMessage = statusMessage + ':';
			}
			statusMessage = statusMessage + " id='" + id + '\''; //$NON-NLS-1$
		}
		if (extraAttributeName != null) {
			if ((element != null) || (id != null)) {
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

	protected static final boolean checkClass(
			final IConfigurationElement configurationElement,
			final List warningsToLog, final String message, final String id) {
		if ((configurationElement.getAttribute(ATT_CLASS) == null)
				&& (configurationElement.getChildren(TAG_CLASS).length == 0)) {
			addWarning(warningsToLog, message, configurationElement, id);
			return false;
		}

		return true;
	}

	protected static final boolean isPulldown(
			final IConfigurationElement element) {
		final String style = readOptional(element, ATT_STYLE);
		final boolean pulldown = readBoolean(element, ATT_PULLDOWN, false);
		return (pulldown || STYLE_PULLDOWN.equals(style));
	}

	protected static final void logWarnings(final List warningsToLog,
			final String message) {
		if ((warningsToLog != null) && (!warningsToLog.isEmpty())) {
			final IStatus status = new MultiStatus(
					WorkbenchPlugin.PI_WORKBENCH, 0, (IStatus[]) warningsToLog
							.toArray(new IStatus[warningsToLog.size()]),
					message, null);
			WorkbenchPlugin.log(status);
		}
	}

	protected static final boolean readBoolean(
			final IConfigurationElement configurationElement,
			final String attribute, final boolean defaultValue) {
		final String value = configurationElement.getAttribute(attribute);
		if (value == null) {
			return defaultValue;
		}

		if (defaultValue) {
			return !value.equalsIgnoreCase("false"); //$NON-NLS-1$
		}

		return value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	protected static final String readOptional(
			final IConfigurationElement configurationElement,
			final String attribute) {
		String value = configurationElement.getAttribute(attribute);
		if ((value != null) && (value.length() == 0)) {
			value = null;
		}

		return value;
	}

	protected static final ParameterizedCommand readParameterizedCommand(
			final IConfigurationElement configurationElement,
			final ICommandService commandService, final List warningsToLog,
			final String message, final String id) {
		final String commandId = readRequired(configurationElement,
				ATT_COMMAND_ID, warningsToLog, message, id);
		if (commandId == null) {
			return null;
		}

		final Command command = commandService.getCommand(commandId);
		final ParameterizedCommand parameterizedCommand = readParameters(
				configurationElement, warningsToLog, command);

		return parameterizedCommand;
	}

	protected static final ParameterizedCommand readParameters(
			final IConfigurationElement configurationElement,
			final List warningsToLog, final Command command) {
		final IConfigurationElement[] parameterElements = configurationElement
				.getChildren(TAG_PARAMETER);
		if ((parameterElements == null) || (parameterElements.length == 0)) {
			return new ParameterizedCommand(command, null);
		}

		final Collection parameters = new ArrayList();
		for (int i = 0; i < parameterElements.length; i++) {
			final IConfigurationElement parameterElement = parameterElements[i];

			final String id = parameterElement.getAttribute(ATT_ID);
			if ((id == null) || (id.length() == 0)) {
				addWarning(warningsToLog, "Parameters need a name", //$NON-NLS-1$
						configurationElement);
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
						"Could not find a matching parameter", //$NON-NLS-1$
						configurationElement, id);
				continue;
			}

			final String value = parameterElement.getAttribute(ATT_VALUE);
			if ((value == null) || (value.length() == 0)) {
				addWarning(warningsToLog, "Parameters need a value", //$NON-NLS-1$
						configurationElement, id);
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

	protected static final String readRequired(
			final IConfigurationElement configurationElement,
			final String attribute, final List warningsToLog,
			final String message) {
		return readRequired(configurationElement, attribute, warningsToLog,
				message, null);
	}

	protected static final String readRequired(
			final IConfigurationElement configurationElement,
			final String attribute, final List warningsToLog,
			final String message, final String id) {
		final String value = configurationElement.getAttribute(attribute);
		if ((value == null) || (value.length() == 0)) {
			addWarning(warningsToLog, message, configurationElement, id);
			return null;
		}

		return value;
	}

	protected static final Expression readWhenElement(
			final IConfigurationElement parentElement,
			final String whenElementName, final String id,
			final List warningsToLog) {
		final IConfigurationElement[] whenElements = parentElement
				.getChildren(whenElementName);
		Expression whenExpression = null;
		if (whenElements.length > 0) {
			if (whenElements.length > 1) {
				addWarning(warningsToLog,
						"There should only be one when element", parentElement, //$NON-NLS-1$
						id, "whenElementName", //$NON-NLS-1$
						whenElementName);
				return ERROR_EXPRESSION;
			}

			final IConfigurationElement whenElement = whenElements[0];
			final IConfigurationElement[] expressionElements = whenElement
					.getChildren();
			if (expressionElements.length > 0) {
				if (expressionElements.length > 1) {
					addWarning(
							warningsToLog,
							"There should only be one expression element", parentElement, //$NON-NLS-1$
							id, "whenElementName", //$NON-NLS-1$
							whenElementName);
					return ERROR_EXPRESSION;
				}

				final ElementHandler elementHandler = ElementHandler
						.getDefault();
				final ExpressionConverter converter = ExpressionConverter
						.getDefault();
				final IConfigurationElement expressionElement = expressionElements[0];
				try {
					whenExpression = elementHandler.create(converter,
							expressionElement);
				} catch (final CoreException e) {
					addWarning(
							warningsToLog,
							"Problem creating when element", //$NON-NLS-1$
							parentElement, id,
							"whenElementName", whenElementName); //$NON-NLS-1$
					return ERROR_EXPRESSION;
				}
			}
		}

		return whenExpression;
	}

	private final IRegistryChangeListener registryChangeListener;

	protected boolean registryListenerAttached = false;

	protected RegistryPersistence() {
		registryChangeListener = new IRegistryChangeListener() {
			@Override
			public final void registryChanged(final IRegistryChangeEvent event) {
				if (isChangeImportant(event)) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public final void run() {
							read();
						}
					});
				}
			}
		};
	}

	@Override
	public void dispose() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeRegistryChangeListener(registryChangeListener);
		registryListenerAttached = false;
	}

	protected abstract boolean isChangeImportant(
			final IRegistryChangeEvent event);

	protected void read() {
		if (!registryListenerAttached) {
			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			registry.addRegistryChangeListener(registryChangeListener);
			registryListenerAttached = true;
		}
	}
}


package org.eclipse.ui.internal.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.RegistryPersistence;
import org.eclipse.ui.services.IEvaluationService;

public final class HandlerPersistence extends RegistryPersistence {

	private static final int INDEX_COMMAND_DEFINITIONS = 0;

	private static final int INDEX_HANDLER_DEFINITIONS = 1;

	private static final int INDEX_HANDLER_SUBMISSIONS = 2;

	private final Collection handlerActivations = new ArrayList();

	private final IHandlerService handlerService;

	private IEvaluationService evaluationService;

	HandlerPersistence(final IHandlerService handlerService,
			IEvaluationService evaluationService) {
		this.handlerService = handlerService;
		this.evaluationService = evaluationService;
	}

	private final void clearActivations(final IHandlerService handlerService) {
		handlerService.deactivateHandlers(handlerActivations);
		Iterator i = handlerActivations.iterator();
		while (i.hasNext()) {
			IHandlerActivation activation = (IHandlerActivation) i.next();
			if (activation.getHandler() != null) {
				try {
					activation.getHandler().dispose();
				} catch (Exception e) {
					WorkbenchPlugin.log("Failed to dispose handler for " //$NON-NLS-1$
							+ activation.getCommandId(), e);
				} catch (LinkageError e) {
					WorkbenchPlugin.log("Failed to dispose handler for " //$NON-NLS-1$
							+ activation.getCommandId(), e);
				}
			}
		}
		handlerActivations.clear();
	}

	@Override
	public final void dispose() {
		super.dispose();
		clearActivations(handlerService);
	}

	@Override
	protected final boolean isChangeImportant(final IRegistryChangeEvent event) {
		return false;
	}

	public boolean handlersNeedUpdating(final IRegistryChangeEvent event) {
		final IExtensionDelta[] handlerDeltas = event.getExtensionDeltas(
				PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_HANDLERS);
		if (handlerDeltas.length == 0) {
			final IExtensionDelta[] commandDeltas = event.getExtensionDeltas(
					PlatformUI.PLUGIN_ID,
					IWorkbenchRegistryConstants.PL_COMMANDS);
			if (commandDeltas.length == 0) {
				final IExtensionDelta[] actionDefinitionDeltas = event
						.getExtensionDeltas(
								PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_ACTION_DEFINITIONS);
				if (actionDefinitionDeltas.length == 0) {
					return false;
				}
			}
		}

		return true;
	}
	
	@Override
	protected final void read() {
		super.read();
		reRead();
	}

	public final void reRead() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		int commandDefinitionCount = 0;
		int handlerDefinitionCount = 0;
		int handlerSubmissionCount = 0;
		final IConfigurationElement[][] indexedConfigurationElements = new IConfigurationElement[3][];

		final IConfigurationElement[] commandsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_COMMANDS);
		for (int i = 0; i < commandsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = commandsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_HANDLER_SUBMISSION.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_HANDLER_SUBMISSIONS, handlerSubmissionCount++);
			} else if (TAG_COMMAND.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_COMMAND_DEFINITIONS, commandDefinitionCount++);
			}
		}

		final IConfigurationElement[] handlersExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_HANDLERS);
		for (int i = 0; i < handlersExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = handlersExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_HANDLER.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_HANDLER_DEFINITIONS, handlerDefinitionCount++);
			}
		}

		clearActivations(handlerService);
		readDefaultHandlersFromRegistry(
				indexedConfigurationElements[INDEX_COMMAND_DEFINITIONS],
				commandDefinitionCount);
		readHandlerSubmissionsFromRegistry(
				indexedConfigurationElements[INDEX_HANDLER_SUBMISSIONS],
				handlerSubmissionCount);
		readHandlersFromRegistry(
				indexedConfigurationElements[INDEX_HANDLER_DEFINITIONS],
				handlerDefinitionCount);
	}
	
	private final void readDefaultHandlersFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount) {
		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String commandId = readOptional(configurationElement, ATT_ID);
			if (commandId == null) {
				continue;
			}

			if ((configurationElement.getAttribute(ATT_DEFAULT_HANDLER) == null)
					&& (configurationElement.getChildren(TAG_DEFAULT_HANDLER).length == 0)) {
				continue;
			}

			handlerActivations.add(handlerService
					.activateHandler(commandId, new HandlerProxy(commandId,
							configurationElement, ATT_DEFAULT_HANDLER)));
		}
	}

	private final void readHandlersFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount) {
		final List warningsToLog = new ArrayList(1);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String commandId = readRequired(configurationElement,
					ATT_COMMAND_ID, warningsToLog, "Handlers need a command id"); //$NON-NLS-1$
			if (commandId == null) {
				continue;
			}

			if (!checkClass(configurationElement, warningsToLog,
					"Handlers need a class", commandId)) { //$NON-NLS-1$
				continue;
			}

			final Expression activeWhenExpression = readWhenElement(
					configurationElement, TAG_ACTIVE_WHEN, commandId,
					warningsToLog);
			if (activeWhenExpression == ERROR_EXPRESSION) {
				continue;
			}
			final Expression enabledWhenExpression = readWhenElement(
					configurationElement, TAG_ENABLED_WHEN, commandId,
					warningsToLog);
			if (enabledWhenExpression == ERROR_EXPRESSION) {
				continue;
			}

			final IHandler proxy = new HandlerProxy(commandId, configurationElement,
					ATT_CLASS, enabledWhenExpression, evaluationService);
			handlerActivations.add(handlerService.activateHandler(commandId,
					proxy, activeWhenExpression));

			final String helpContextId = readOptional(configurationElement,
					ATT_HELP_CONTEXT_ID);
			handlerService.setHelpContextId(proxy, helpContextId);
		}

		logWarnings(
				warningsToLog,
				"Warnings while parsing the handlers from the 'org.eclipse.ui.handlers' extension point."); //$NON-NLS-1$
	}

	private final void readHandlerSubmissionsFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount) {
		final List warningsToLog = new ArrayList(1);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String commandId = readRequired(configurationElement,
					ATT_COMMAND_ID, warningsToLog,
					"Handler submissions need a command id"); //$NON-NLS-1$
			if (commandId == null) {
				continue;
			}

			handlerActivations.add(handlerService.activateHandler(commandId,
					new LegacyHandlerWrapper(new LegacyHandlerProxy(
							configurationElement))));
		}

		logWarnings(
				warningsToLog,
				"Warnings while parsing the handler submissions from the 'org.eclipse.ui.commands' extension point."); //$NON-NLS-1$
	}
}

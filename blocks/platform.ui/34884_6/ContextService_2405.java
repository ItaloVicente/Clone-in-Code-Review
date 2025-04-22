
package org.eclipse.ui.internal.contexts;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.RegistryPersistence;

public final class ContextPersistence extends RegistryPersistence {

	private static final int INDEX_CONTEXT_DEFINITIONS = 0;

	private static final void readContextsFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount,
			final ContextManager contextManager) {
		final List warningsToLog = new ArrayList(1);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String contextId = readRequired(configurationElement, ATT_ID,
					warningsToLog, "Contexts need an id"); //$NON-NLS-1$
			if (contextId == null) {
				continue;
			}

			final String name = readRequired(configurationElement, ATT_NAME,
					warningsToLog, "Contexts need a name", //$NON-NLS-1$
					contextId);
			if (name == null) {
				continue;
			}

			final String description = readOptional(configurationElement,
					ATT_DESCRIPTION);

			String parentId = configurationElement.getAttribute(ATT_PARENT_ID);
			if ((parentId == null) || (parentId.length() == 0)) {
				parentId = configurationElement.getAttribute(ATT_PARENT);
				if ((parentId == null) || (parentId.length() == 0)) {
					parentId = configurationElement
							.getAttribute(ATT_PARENT_SCOPE);
				}
			}
			if ((parentId != null) && (parentId.length() == 0)) {
				parentId = null;
			}

			final Context context = contextManager.getContext(contextId);
			if (!context.isDefined()) {
				context.define(name, description, parentId);
			}
		}

		logWarnings(
				warningsToLog,
				"Warnings while parsing the contexts from the 'org.eclipse.ui.contexts', 'org.eclipse.ui.commands' and 'org.eclipse.ui.acceleratorScopes' extension points."); //$NON-NLS-1$
	}

	private final ContextManager contextManager;

	public ContextPersistence(final ContextManager contextManager) {
		if (contextManager == null) {
			throw new NullPointerException(
					"The context manager must not be null"); //$NON-NLS-1$
		}
		this.contextManager = contextManager;
	}

	@Override
	protected final boolean isChangeImportant(final IRegistryChangeEvent event) {
		final IExtensionDelta[] acceleratorScopeDeltas = event
				.getExtensionDeltas(PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_ACCELERATOR_SCOPES);
		if (acceleratorScopeDeltas.length == 0) {
			final IExtensionDelta[] contextDeltas = event.getExtensionDeltas(
					PlatformUI.PLUGIN_ID,
					IWorkbenchRegistryConstants.PL_CONTEXTS);
			if (contextDeltas.length == 0) {
				final IExtensionDelta[] commandDeltas = event
						.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_COMMANDS);
				if (commandDeltas.length == 0) {
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

	public void reRead() {

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		int contextDefinitionCount = 0;
		final IConfigurationElement[][] indexedConfigurationElements = new IConfigurationElement[1][];

		final IConfigurationElement[] acceleratorScopesExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_ACCELERATOR_SCOPES);
		for (int i = 0; i < acceleratorScopesExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = acceleratorScopesExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_ACCELERATOR_SCOPE.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_CONTEXT_DEFINITIONS, contextDefinitionCount++);
			}
		}

		final IConfigurationElement[] commandsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_COMMANDS);
		for (int i = 0; i < commandsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = commandsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_SCOPE.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_CONTEXT_DEFINITIONS, contextDefinitionCount++);
			} else if (TAG_CONTEXT.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_CONTEXT_DEFINITIONS, contextDefinitionCount++);

			}
		}

		final IConfigurationElement[] contextsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_CONTEXTS);
		for (int i = 0; i < contextsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = contextsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_CONTEXT.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_CONTEXT_DEFINITIONS, contextDefinitionCount++);
			}
		}

		readContextsFromRegistry(
				indexedConfigurationElements[INDEX_CONTEXT_DEFINITIONS],
				contextDefinitionCount, contextManager);
	}

}

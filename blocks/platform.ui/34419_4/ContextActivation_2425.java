
package org.eclipse.ui.internal.contexts;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.commands.contexts.ContextManagerEvent;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.services.IServiceLocator;

public final class ActiveContextSourceProvider extends AbstractSourceProvider
		implements IContextManagerListener {

	private static final String[] PROVIDED_SOURCE_NAMES = new String[] { ISources.ACTIVE_CONTEXT_NAME };

	private IContextService contextService;

	@Override
	public final void contextManagerChanged(final ContextManagerEvent event) {
		if (event.isActiveContextsChanged()) {
			final Map currentState = getCurrentState();

			if (DEBUG) {
				logDebuggingInfo("Contexts changed to " //$NON-NLS-1$
						+ currentState.get(ISources.ACTIVE_CONTEXT_NAME));
			}

			fireSourceChanged(ISources.ACTIVE_CONTEXT, currentState);
		}
	}

	@Override
	public final void dispose() {
		contextService.removeContextManagerListener(this);
	}

	@Override
	public final Map getCurrentState() {
		final Map currentState = new TreeMap();
		final Collection activeContextIds = contextService
				.getActiveContextIds();
		currentState.put(ISources.ACTIVE_CONTEXT_NAME, activeContextIds);
		return currentState;
	}

	@Override
	public final String[] getProvidedSourceNames() {
		return PROVIDED_SOURCE_NAMES;
	}

	@Override
	public void initialize(IServiceLocator locator) {
		contextService = locator
				.getService(IContextService.class);
		contextService.addContextManagerListener(this);
	}
}

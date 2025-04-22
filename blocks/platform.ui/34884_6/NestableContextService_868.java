package org.eclipse.ui.internal.contexts;

import java.util.Set;

import org.eclipse.core.commands.contexts.ContextEvent;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.commands.contexts.ContextManagerEvent;
import org.eclipse.core.commands.contexts.IContextListener;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.ui.contexts.IContext;

public class LegacyContextListenerWrapper implements IContextListener,
		IContextManagerListener {

	private final IContext context;

	private final ContextManager contextManager;

	private final org.eclipse.ui.contexts.IContextListener wrappedListener;

	public LegacyContextListenerWrapper(
			final org.eclipse.ui.contexts.IContextListener listener,
			final ContextManager contextManager, final IContext context) {
		if (listener == null) {
			throw new NullPointerException(
					"Cannot create a listener wrapper on a null listener"); //$NON-NLS-1$
		}

		if (contextManager == null) {
			throw new NullPointerException(
					"Cannot create a listener wrapper with a null context manager"); //$NON-NLS-1$
		}

		if (context == null) {
			throw new NullPointerException(
					"Cannot create a listener wrapper with a null context"); //$NON-NLS-1$
		}

		wrappedListener = listener;
		this.contextManager = contextManager;
		this.context = context;
	}

	@Override
	public final void contextChanged(final ContextEvent contextEvent) {
		wrappedListener
				.contextChanged(new org.eclipse.ui.contexts.ContextEvent(
						new ContextLegacyWrapper(contextEvent.getContext(),
								contextManager), contextEvent
								.isDefinedChanged(), false, contextEvent
								.isNameChanged(), contextEvent
								.isParentIdChanged()));
	}

	@Override
	public final void contextManagerChanged(final ContextManagerEvent event) {
		final String contextId = context.getId();
		final boolean enabledChanged;
		if (event.isActiveContextsChanged()) {
			final Set previousContexts = event.getPreviouslyActiveContextIds();
			final Set currentContexts = contextManager.getActiveContextIds();
			if ((previousContexts != null)
					&& (previousContexts.contains(contextId))
					&& ((currentContexts == null) || (currentContexts
							.contains(contextId)))) {
				enabledChanged = true;
			} else if ((currentContexts != null)
					&& (currentContexts.contains(contextId))
					&& ((previousContexts == null) || (previousContexts
							.contains(contextId)))) {
				enabledChanged = true;
			} else {
				enabledChanged = false;
			}
		} else {
			enabledChanged = false;
		}

		wrappedListener
				.contextChanged(new org.eclipse.ui.contexts.ContextEvent(
						context, false, enabledChanged, false, false));
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyContextListenerWrapper) {
			final LegacyContextListenerWrapper other = (LegacyContextListenerWrapper) object;
			return wrappedListener.equals(other.wrappedListener);
		}

		if (object instanceof org.eclipse.ui.contexts.IContextListener) {
			final org.eclipse.ui.contexts.IContextListener other = (org.eclipse.ui.contexts.IContextListener) object;
			return wrappedListener.equals(other);
		}

		return false;
	}

	@Override
	public final int hashCode() {
		return wrappedListener.hashCode();
	}
}

package org.eclipse.ui.internal.contexts;

import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.ui.contexts.IContext;
import org.eclipse.ui.contexts.IContextListener;
import org.eclipse.ui.contexts.NotDefinedException;
import org.eclipse.ui.internal.util.Util;

public class ContextLegacyWrapper implements IContext {

	private final ContextManager contextManager;

	private final Context wrappedContext;

	public ContextLegacyWrapper(final Context context,
			final ContextManager contextManager) {
		if (context == null) {
			throw new NullPointerException(
					"A wrapper cannot be created on a null context"); //$NON-NLS-1$
		}

		if (contextManager == null) {
			throw new NullPointerException(
					"A wrapper cannot be created with a null manager"); //$NON-NLS-1$
		}

		wrappedContext = context;
		this.contextManager = contextManager;
	}

	@Override
	public void addContextListener(IContextListener contextListener) {
		final LegacyContextListenerWrapper wrapper = new LegacyContextListenerWrapper(
				contextListener, contextManager, this);
		wrappedContext.addContextListener(wrapper);

		contextManager.addContextManagerListener(wrapper);
	}

	@Override
	public int compareTo(Object o) {
		return Util
				.compare(wrappedContext, ((ContextLegacyWrapper) o).wrappedContext);
	}

	@Override
	public String getId() {
		return wrappedContext.getId();
	}

	@Override
	public String getName() throws NotDefinedException {
		try {
			return wrappedContext.getName();
		} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			throw new NotDefinedException(e);
		}
	}

	@Override
	public String getParentId() throws NotDefinedException {
		try {
			return wrappedContext.getParentId();
		} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			throw new NotDefinedException(e);
		}
	}

	@Override
	public boolean isDefined() {
		return wrappedContext.isDefined();
	}

	@Override
	public boolean isEnabled() {
		return contextManager.getActiveContextIds().contains(
				wrappedContext.getId());
	}

	@Override
	public void removeContextListener(IContextListener contextListener) {
		final LegacyContextListenerWrapper wrapper = new LegacyContextListenerWrapper(
				contextListener, contextManager, this);
		wrappedContext.removeContextListener(wrapper);
		contextManager.removeContextManagerListener(wrapper);
	}

}

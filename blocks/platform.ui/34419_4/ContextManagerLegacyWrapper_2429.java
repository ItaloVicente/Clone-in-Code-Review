
package org.eclipse.ui.internal.contexts;

import org.eclipse.core.commands.contexts.ContextManager;

public final class ContextManagerFactory {

	public static final ContextManagerLegacyWrapper getContextManagerWrapper(
			final ContextManager contextManager) {
		return new ContextManagerLegacyWrapper(contextManager);
	}

	private ContextManagerFactory() {
	}
}

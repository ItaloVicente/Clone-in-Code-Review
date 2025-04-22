
package org.eclipse.ui;

import java.util.Map;
import org.eclipse.core.commands.util.Tracing;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.services.IServiceLocator;

public abstract class AbstractSourceProvider implements ISourceProvider {

	protected static boolean DEBUG = Policy.DEBUG_SOURCES;

	private final ListenerList listeners = new ListenerList(ListenerList.IDENTITY);


	@Override
	public final void addSourceProviderListener(
			final ISourceProviderListener listener) {
		if (listener == null) {
			throw new NullPointerException("The listener cannot be null"); //$NON-NLS-1$
		}

		listeners.add(listener);
	}

	protected final void fireSourceChanged(final int sourcePriority,
			final String sourceName, final Object sourceValue) {
		for (Object listener : listeners.getListeners()) {
			((ISourceProviderListener) listener).sourceChanged(sourcePriority, sourceName,
					sourceValue);
		}
	}

	protected final void fireSourceChanged(final int sourcePriority,
			final Map sourceValuesByName) {

		for (Object listener : listeners.getListeners()) {
			((ISourceProviderListener) listener).sourceChanged(sourcePriority, sourceValuesByName);
		}
	}

	protected final void logDebuggingInfo(final String message) {
		if (DEBUG && (message != null)) {
			Tracing.printTrace("SOURCES", message); //$NON-NLS-1$
		}
	}

	@Override
	public final void removeSourceProviderListener(
			final ISourceProviderListener listener) {
		if (listener == null) {
			throw new NullPointerException("The listener cannot be null"); //$NON-NLS-1$
		}

		listeners.remove(listener);
	}

	public void initialize(final IServiceLocator locator) {
	}
}

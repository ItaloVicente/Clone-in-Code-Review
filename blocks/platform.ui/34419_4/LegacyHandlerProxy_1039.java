package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.commands.HandlerEvent;
import org.eclipse.ui.commands.IHandlerListener;
import org.eclipse.ui.internal.commands.ILegacyAttributeNames;

public final class LegacyHandlerListenerWrapper implements IHandlerListener {

	private final IHandler handler;

	private final org.eclipse.core.commands.IHandlerListener listener;

	public LegacyHandlerListenerWrapper(final IHandler handler,
			final org.eclipse.core.commands.IHandlerListener listener) {
		if (handler == null) {
			throw new NullPointerException(
					"A listener wrapper cannot be created on a null handler"); //$NON-NLS-1$
		}

		if (listener == null) {
			throw new NullPointerException(
					"A listener wrapper cannot be created on a null listener"); //$NON-NLS-1$
		}

		this.handler = handler;
		this.listener = listener;
	}

	@Override
	public void handlerChanged(HandlerEvent event) {
		final boolean enabledChanged = ((Boolean) event
				.getPreviousAttributeValuesByName().get(
						ILegacyAttributeNames.ENABLED)).booleanValue() != handler
				.isEnabled();
		final boolean handledChanged = ((Boolean) event
				.getPreviousAttributeValuesByName().get(
						ILegacyAttributeNames.HANDLED)).booleanValue() != handler
				.isHandled();
		listener.handlerChanged(new org.eclipse.core.commands.HandlerEvent(
				handler, enabledChanged, handledChanged));
	}
}

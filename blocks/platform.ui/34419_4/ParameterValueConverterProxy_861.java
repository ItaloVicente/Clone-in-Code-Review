package org.eclipse.ui.internal.commands;

import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.ui.commands.ICommand;

final class LegacyCommandListenerWrapper implements ICommandListener {

	private final BindingManager bindingManager;

	private final org.eclipse.ui.commands.ICommandListener listener;

	LegacyCommandListenerWrapper(
			final org.eclipse.ui.commands.ICommandListener listener,
			final BindingManager bindingManager) {
		if (listener == null) {
			throw new NullPointerException("Cannot wrap a null listener."); //$NON-NLS-1$
		}

		if (bindingManager == null) {
			throw new NullPointerException(
					"Cannot create a listener wrapper without a binding manager"); //$NON-NLS-1$
		}

		this.listener = listener;
		this.bindingManager = bindingManager;
	}

	@Override
	public final void commandChanged(final CommandEvent commandEvent) {
		final ICommand command = new CommandLegacyWrapper(commandEvent.getCommand(),
				bindingManager);
		final boolean definedChanged = commandEvent.isDefinedChanged();
		final boolean descriptionChanged = commandEvent.isDescriptionChanged();
		final boolean handledChanged = commandEvent.isHandledChanged();
		final boolean nameChanged = commandEvent.isNameChanged();

		listener.commandChanged(new org.eclipse.ui.commands.CommandEvent(
				command, false, false, definedChanged, descriptionChanged,
				handledChanged, false, nameChanged, null));

	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyCommandListenerWrapper) {
			final LegacyCommandListenerWrapper wrapper = (LegacyCommandListenerWrapper) object;
			return listener.equals(wrapper.listener);
		}

		if (object instanceof org.eclipse.ui.commands.ICommandListener) {
			final org.eclipse.ui.commands.ICommandListener other = (org.eclipse.ui.commands.ICommandListener) object;
			return listener.equals(other);
		}

		return false;
	}

	@Override
	public final int hashCode() {
		return listener.hashCode();
	}
}

package org.eclipse.ui.internal.commands;

public final class CommandImageManagerEvent {

	private final String[] changedCommandIds;

	private final CommandImageManager commandImageManager;

	private final String style;

	private final int type;

	CommandImageManagerEvent(final CommandImageManager commandImageManager,
			final String[] changedCommandIds, final int type, final String style) {
		if (commandImageManager == null) {
			throw new NullPointerException("An event must refer to its manager"); //$NON-NLS-1$
		}

		if ((changedCommandIds == null) || (changedCommandIds.length < 1)) {
			throw new IllegalArgumentException(
					"There must be at least one change command identifier"); //$NON-NLS-1$
		}

		this.commandImageManager = commandImageManager;
		this.changedCommandIds = changedCommandIds;
		this.type = type;
		this.style = style;
	}

	public final String[] getChangedCommandIds() {
		final String[] copy = new String[changedCommandIds.length];
		System.arraycopy(changedCommandIds, 0, copy, 0,
				changedCommandIds.length);
		return copy;
	}

	public final CommandImageManager getCommandImageManager() {
		return commandImageManager;
	}

	public final boolean isCommandIdChanged(final String commandId) {
		for (int i = 0; i < changedCommandIds.length; i++) {
			if (commandId.equals(changedCommandIds[i])) {
				return true;
			}
		}

		return false;
	}

	public final boolean isCommandImageChanged(final String commandId) {
		return isCommandIdChanged(commandId)
				&& (type == CommandImageManager.TYPE_DEFAULT)
				&& (style == null);
	}

	public final boolean isCommandImageChanged(final String commandId,
			final int type) {
		return isCommandIdChanged(commandId)
				&& ((type == CommandImageManager.TYPE_DEFAULT) || (type == this.type))
				&& (style == null);
	}

	public final boolean isCommandImageChanged(final String commandId,
			final int type, final String style) {
		return isCommandIdChanged(commandId)
				&& ((type == CommandImageManager.TYPE_DEFAULT) || (type == this.type))
				&& ((style == null) || (style.equals(this.style)));
	}

	public final boolean isCommandImageChanged(final String commandId,
			final String style) {
		return isCommandIdChanged(commandId)
				&& (type == CommandImageManager.TYPE_DEFAULT)
				&& ((style == null) || (style.equals(this.style)));
	}
}

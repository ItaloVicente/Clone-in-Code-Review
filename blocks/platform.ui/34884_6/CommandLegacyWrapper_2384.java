package org.eclipse.ui.internal.commands;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.commands.ICommandImageService;
import org.eclipse.ui.commands.ICommandService;

public final class CommandImageService implements ICommandImageService {

	private final CommandImageManager commandImageManager;

	private final CommandImagePersistence commandImagePersistence;

	public CommandImageService(final CommandImageManager commandImageManager,
			final ICommandService commandService) {
		if (commandImageManager == null) {
			throw new NullPointerException(
					"Cannot create a command image service with a null manager"); //$NON-NLS-1$
		}
		if (commandService == null) {
			throw new NullPointerException(
					"Cannot create a command image service with a null command service"); //$NON-NLS-1$
		}
		this.commandImageManager = commandImageManager;
		this.commandImagePersistence = new CommandImagePersistence(
				commandImageManager, commandService);
	}

	public final void bind(final String commandId, final int type,
			final String style, final ImageDescriptor descriptor) {
		commandImageManager.bind(commandId, type, style, descriptor);
	}

	public final void bind(final String commandId, final int type,
			final String style, final URL url) {
		commandImageManager.bind(commandId, type, style, url);
	}

	@Override
	public final void dispose() {
		commandImagePersistence.dispose();
	}

	public final String generateUnusedStyle(final String commandId) {
		return commandImageManager.generateUnusedStyle(commandId);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId) {
		return commandImageManager.getImageDescriptor(commandId);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type) {
		return commandImageManager.getImageDescriptor(commandId, type);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type, final String style) {
		return commandImageManager.getImageDescriptor(commandId, type, style);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final String style) {
		return commandImageManager.getImageDescriptor(commandId, style);
	}

	public final void readRegistry() {
		commandImagePersistence.read();
	}
}

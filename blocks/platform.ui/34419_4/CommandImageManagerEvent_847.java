package org.eclipse.ui.internal.commands;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.jface.resource.ImageDescriptor;

public final class CommandImageManager extends EventManager {

	public static final int TYPE_DEFAULT = 0;

	public static final int TYPE_DISABLED = 1;

	public static final int TYPE_HOVER = 2;

	private final Map imagesById = new HashMap();

	public final void addCommandImageManagerListener(
			final ICommandImageManagerListener listener) {
		addListenerObject(listener);
	}

	public final void bind(final String commandId, final int type,
			final String style, final URL url) {
		final ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
		bind(commandId, type, style, descriptor);
	}

	public final void bind(final String commandId, final int type,
			final String style, final ImageDescriptor descriptor) {
		Object[] images = (Object[]) imagesById.get(commandId);
		if (images == null) {
			images = new Object[3];
			imagesById.put(commandId, images);
		}

		if ((type < 0) || (type >= images.length)) {
			throw new IllegalArgumentException(
					"The type must be one of TYPE_DEFAULT, TYPE_DISABLED and TYPE_HOVER."); //$NON-NLS-1$
		}

		final Object typedImage = images[type];
		if (style == null) {
			if ((typedImage == null) || (typedImage instanceof ImageDescriptor)) {
				images[type] = descriptor;
			} else if (typedImage instanceof Map) {
				final Map styleMap = (Map) typedImage;
				styleMap.put(style, descriptor);
			}
		} else {
			if (typedImage instanceof Map) {
				final Map styleMap = (Map) typedImage;
				styleMap.put(style, descriptor);
			} else if (typedImage instanceof ImageDescriptor
					|| typedImage == null) {
				final Map styleMap = new HashMap();
				styleMap.put(null, typedImage);
				styleMap.put(style, descriptor);
				images[type] = styleMap;
			}
		}

		fireManagerChanged(new CommandImageManagerEvent(this,
				new String[] { commandId }, type, style));
	}

	public final void clear() {
		imagesById.clear();
		if (isListenerAttached()) {
			final String[] commandIds = (String[]) imagesById.keySet().toArray(
					new String[imagesById.size()]);
			fireManagerChanged(new CommandImageManagerEvent(this, commandIds,
					TYPE_DEFAULT, null));
		}
	}

	private final void fireManagerChanged(final CommandImageManagerEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}

		final Object[] listeners = getListeners();
		for (int i = 0; i < listeners.length; i++) {
			final ICommandImageManagerListener listener = (ICommandImageManagerListener) listeners[i];
			listener.commandImageManagerChanged(event);
		}
	}

	public final String generateUnusedStyle(final String commandId) {
		final Object[] existingImages = (Object[]) imagesById.get(commandId);
		if (existingImages == null) {
			return null;
		}

		final Set existingStyles = new HashSet(3);
		for (int type = 0; type < existingImages.length; type++) {
			final Object styledImages = existingImages[type];
			if (styledImages instanceof ImageDescriptor) {
				existingStyles.add(null);
			} else if (styledImages instanceof Map) {
				final Map styleMap = (Map) styledImages;
				existingStyles.addAll(styleMap.keySet());
			}
		}

		if (!existingStyles.contains(null)) {
			return null;
		}

		String generatedStyle = "AUTOGEN:::"; //$NON-NLS-1$
		int index = 0;
		while (existingStyles.contains(generatedStyle)) {
			generatedStyle += (index++ % 10);
		}

		return generatedStyle;
	}

	public final ImageDescriptor getImageDescriptor(final String commandId) {
		return getImageDescriptor(commandId, TYPE_DEFAULT, null);
	}

	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type) {
		return getImageDescriptor(commandId, type, null);
	}

	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type, final String style) {
		if (commandId == null) {
			throw new NullPointerException();
		}

		final Object[] images = (Object[]) imagesById.get(commandId);
		if (images == null) {
			return null;
		}

		if ((type < 0) || (type >= images.length)) {
			throw new IllegalArgumentException(
					"The type must be one of TYPE_DEFAULT, TYPE_DISABLED and TYPE_HOVER."); //$NON-NLS-1$
		}

		Object typedImage = images[type];

		if (typedImage == null) {
			return null;
		}

		if (typedImage instanceof ImageDescriptor) {
			return (ImageDescriptor) typedImage;
		}

		if (typedImage instanceof Map) {
			final Map styleMap = (Map) typedImage;
			Object styledImage = styleMap.get(style);
			if (styledImage instanceof ImageDescriptor) {
				return (ImageDescriptor) styledImage;
			}

			if (style != null) {
				styledImage = styleMap.get(null);
				if (styledImage instanceof ImageDescriptor) {
					return (ImageDescriptor) styledImage;
				}
			}
		}

		return null;
	}

	public final ImageDescriptor getImageDescriptor(final String commandId,
			final String style) {
		return getImageDescriptor(commandId, TYPE_DEFAULT, style);
	}

	public final void removeCommandImageManagerListener(
			final ICommandImageManagerListener listener) {
		removeListenerObject(listener);
	}
}

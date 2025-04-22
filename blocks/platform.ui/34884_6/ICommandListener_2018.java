package org.eclipse.ui.commands;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.commands.CommandImageManager;
import org.eclipse.ui.services.IDisposable;

public interface ICommandImageService extends IDisposable {

	public static final int TYPE_DEFAULT = CommandImageManager.TYPE_DEFAULT;

	public static final int TYPE_DISABLED = CommandImageManager.TYPE_DISABLED;

	public static final int TYPE_HOVER = CommandImageManager.TYPE_HOVER;

	public static final String IMAGE_STYLE_DEFAULT = null;

	public static final String IMAGE_STYLE_TOOLBAR = "toolbar"; //$NON-NLS-1$

	public ImageDescriptor getImageDescriptor(String commandId);

	public ImageDescriptor getImageDescriptor(String commandId, int type);

	public ImageDescriptor getImageDescriptor(String commandId, int type,
			String style);

	public ImageDescriptor getImageDescriptor(String commandId, String style);
}

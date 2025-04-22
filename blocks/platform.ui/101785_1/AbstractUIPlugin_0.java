package org.eclipse.ui.plugin;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractImageManager {

	static private final Set<Class<? extends AbstractImageManager>> classes = new HashSet<>();

	protected AbstractImageManager() {
		if (Display.getCurrent() == null) {
			throw new IllegalStateException("Image Manager can only be used in the UI thread"); //$NON-NLS-1$
		}
		Class<? extends AbstractImageManager> currentClass = getClass();
		if ("org.eclipse.ui.plugin.UiPluginImageManager".equals(currentClass.getName())) { //$NON-NLS-1$
			return;
		}
		if (classes.contains(currentClass)) {
			throw new IllegalStateException(
					"Don't create multiple instances for the same image manager class. As this would load the same images multiple times."); //$NON-NLS-1$
		}
		classes.add(currentClass);
	}

	private ImageRegistry imageRegistry = null;

	private ImageRegistry createImageRegistry() {

		if (Display.getCurrent() != null) {
			return new ImageRegistry(Display.getCurrent());
		}

		if (PlatformUI.isWorkbenchRunning()) {
			return new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
		}

		throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
	}

	protected abstract void initializeImageRegistry(ImageRegistry reg);

	public ImageRegistry getImageRegistry() {
		if (this.imageRegistry == null) {
			this.imageRegistry = createImageRegistry();
			initializeImageRegistry(this.imageRegistry);
		}
		return this.imageRegistry;
	}

	public Image getImage(String key) {
		return this.getImageRegistry().get(key);
	}

	public ImageDescriptor getImageDescriptor(String key) {
		return this.getImageRegistry().getDescriptor(key);
	}

	public void dispose() {
		if (this.imageRegistry != null) {
			this.imageRegistry.dispose();
		}
		this.imageRegistry = null;
	}

	protected void registerImage(String pluginId, String key, String fileName) {

		ImageRegistry registry = getImageRegistry();

		ImageDescriptor desc = AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, fileName);

		if (registry.get(key) != null) {
			throw new IllegalStateException("duplicate imageId in image registry."); //$NON-NLS-1$
		}
		registry.put(key, desc);
	}

}

package org.eclipse.ui.internal.browser;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

class ImageResourceManager {
	private LocalResourceManager manager;

	ImageResourceManager(Control owner) {
		manager = new LocalResourceManager(JFaceResources.getResources(), owner);
	}

	static ImageDescriptor getImageDescriptor(String path) {
		URL url = FileLocator.find(WebBrowserUIPlugin.getInstance().getBundle(), new Path(path), null);
		return ImageDescriptor.createFromURL(url);
	}

	Image getImage(ImageDescriptor descriptor) {
		return (Image) manager.get(descriptor);
	}
}


package org.eclipse.jface.resource;

import java.net.URL;
import java.util.function.Supplier;

import org.eclipse.swt.graphics.ImageData;

class DeferredImageDescriptor extends ImageDescriptor {
	private final Supplier<URL> supplier;
	private final boolean useMissingImage;

	public DeferredImageDescriptor(boolean useMissingImage, Supplier<URL> supplier) {
		this.supplier = supplier;
		this.useMissingImage = useMissingImage;
	}

	@Override
	public ImageData getImageData(int zoom) {
		URL url = supplier.get();
		if (url == null) {
			return useMissingImage ? ImageDescriptor.getMissingImageDescriptor().getImageData(zoom) : null;
		}
		return ImageDescriptor.createFromURL(url).getImageData(zoom);
	}
}

package org.eclipse.jface.resource;

import java.net.URL;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.swt.graphics.ImageData;

final class DeferredImageDescriptor extends ImageDescriptor {
	private final Supplier<URL> supplier;
	private final boolean useMissingImage;

	DeferredImageDescriptor(boolean useMissingImage, Supplier<URL> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
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

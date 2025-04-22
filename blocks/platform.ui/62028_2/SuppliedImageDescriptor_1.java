package org.eclipse.jface.resource;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.ImageData;
import org.osgi.framework.Bundle;

public class LazyImageDescriptor extends ImageDescriptor {

	public interface ImageDescriptorGenerator {
		ImageDescriptor generateImageDescriptor();
	}

	private ImageDescriptorGenerator generator;
	private ImageDescriptor imageDescriptor;

	public LazyImageDescriptor(ImageDescriptorGenerator generator) {
		this.generator = generator;
	}

	public static LazyImageDescriptor initFromFileName(Bundle bundle, String path, Class<?> fallback, String fallbackPath) {
		return new LazyImageDescriptor(() -> {
			if (bundle != null) {
				URL url = FileLocator.find(bundle, new Path(path), null);
				if (url != null)
					return ImageDescriptor.createFromURL(url);
			}
			return ImageDescriptor.createFromFile(fallback, fallbackPath);
		});
	}

	@Override
	public ImageData getImageData() {
		if (imageDescriptor == null) {
			imageDescriptor = generator.generateImageDescriptor();
		}
		return imageDescriptor.getImageData();
	}

}

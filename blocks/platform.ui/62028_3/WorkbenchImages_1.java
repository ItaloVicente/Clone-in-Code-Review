
package org.eclipse.jface.resource;

import java.util.function.Supplier;

import org.eclipse.swt.graphics.ImageData;

class SuppliedImageDescriptor extends ImageDescriptor {

	private Supplier<ImageData> supplier;
	private ImageData imageData;

	SuppliedImageDescriptor(Supplier<ImageData> supplier) {
		this.supplier = supplier;
	}

	@Override
	public ImageData getImageData() {
		if (imageData == null) {
			imageData = supplier.get();
		}
		return imageData;
	}
}

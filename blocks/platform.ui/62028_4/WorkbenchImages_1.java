
package org.eclipse.jface.resource;

import java.util.function.Supplier;

import org.eclipse.swt.graphics.ImageData;

final class SuppliedImageDescriptor extends ImageDescriptor {

	private Supplier<ImageData> supplier;

	SuppliedImageDescriptor(Supplier<ImageData> supplier) {
		this.supplier = supplier;
	}

	@Override
	public ImageData getImageData() {
		return supplier.get();
	}
}

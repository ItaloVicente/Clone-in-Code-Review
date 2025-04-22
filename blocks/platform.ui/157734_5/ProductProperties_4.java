
package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.Optional;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;

public class ProductInformation {
	private final Optional<IProduct> product;
	private String productName;

	public ProductInformation() {
		this(Optional.of(new UnavailableProduct()));
	}

	public ProductInformation(Optional<IProduct> product) {
		if (!product.isPresent()) {
			product = Optional.of(new UnavailableProduct());
		}
		this.product = product;
		this.productName = product.get().getName();
	}

	public String getName() {
		return productName;
	}

	public ImageDescriptor getAboutImageDescriptor() {
		if (JFaceResources.getImage(productName) == null) {
			createAboutImage();
		}

		return JFaceResources.getImageRegistry().getDescriptor(productName);
	}

	private void createAboutImage() {
		Optional<ImageDescriptor> imageDescriptor = ProductProperties.getAboutImage(product);
		if (imageDescriptor.isPresent()) {
			JFaceResources.getImageRegistry().put(productName, imageDescriptor.get());
		}
	}

	public String getAboutText() {
		return ProductProperties.getAboutText(product.get());
	}

	public Image getAboutImage() {
		if (JFaceResources.getImage(productName) == null) {
			createAboutImage();
		}
		return JFaceResources.getImage(productName);
	}

}

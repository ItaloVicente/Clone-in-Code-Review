
package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.Optional;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;

public final class ProductInformation {
	private final IProduct product;
	private final String productName;

	public ProductInformation() {
		this(new UnavailableProduct());
	}

	public ProductInformation(IProduct product) {
		this.product = Optional.ofNullable(product).orElse(new UnavailableProduct());

		this.productName = Optional.ofNullable(product.getName()).orElse("");
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
		Optional<ImageDescriptor> imageDescriptor = ProductProperties.aboutImage(Optional.of(product));
		if (imageDescriptor.isPresent()) {
			JFaceResources.getImageRegistry().put(productName, imageDescriptor.get());
		}
	}

	public String getAboutText() {
		return Optional.ofNullable(ProductProperties.getAboutText(product)).orElse("");
	}

	public Optional<Image> getAboutImage() {
		if (JFaceResources.getImage(productName) == null) {
			createAboutImage();
		}
		return Optional.ofNullable(JFaceResources.getImage(productName));
	}

}

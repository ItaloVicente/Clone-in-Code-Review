
package org.eclipse.e4.ui.internal.dialogs.about;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.e4.ui.dialogs.textbundles.E4DialogMessages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;

public class ProductInformation {
	private final IProduct product;
	private String productName;

	public ProductInformation(IProduct product) {
		this.product = product;
		if (product != null) {
			productName = product.getName();
		} else {
			productName = E4DialogMessages.AboutDialog_defaultProductName;
		}
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
		ImageDescriptor imageDescriptor = ProductProperties.getAboutImage(product);
		JFaceResources.getImageRegistry().put(productName, imageDescriptor);
	}

	public String getAboutText() {
		return ProductProperties.getAboutText(product);
	}

	public Image getAboutImage() {
		if (JFaceResources.getImage(productName) == null) {
			createAboutImage();
		}
		return JFaceResources.getImage(productName);
	}

}

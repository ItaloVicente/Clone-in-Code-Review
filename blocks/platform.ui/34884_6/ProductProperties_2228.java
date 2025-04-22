package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.jface.resource.ImageDescriptor;

public class ProductInfo {
    private IProduct product;

    private String productName;

    private String appName;

    private ImageDescriptor[] windowImages;

    private ImageDescriptor aboutImage;

    private String aboutText;

    public ProductInfo(IProduct product) {
        this.product = product;
    }

    public String getProductName() {
        if (productName == null && product != null) {
			productName = product.getName();
		}
        return productName;
    }

    public String getAppName() {
        if (appName == null && product != null) {
			appName = ProductProperties.getAppName(product);
		}
        return appName;
    }

    public ImageDescriptor getAboutImage() {
        if (aboutImage == null && product != null) {
			aboutImage = ProductProperties.getAboutImage(product);
		}
        return aboutImage;
    }

    public ImageDescriptor[] getWindowImages() {
        if (windowImages == null && product != null) {
			windowImages = ProductProperties.getWindowImages(product);
		}
        return windowImages;
    }

    public String getAboutText() {
        if (aboutText == null && product != null) {
			aboutText = ProductProperties.getAboutText(product);
		}
        return aboutText;
    }
}

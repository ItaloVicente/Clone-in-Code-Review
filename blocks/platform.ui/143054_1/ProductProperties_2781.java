	}

	public static ImageDescriptor getAboutImage(IProduct product) {
		return getImage(product.getProperty(ABOUT_IMAGE), product.getDefiningBundle());
	}

	public static ImageDescriptor[] getWindowImages(IProduct product) {
		String property = product.getProperty(WINDOW_IMAGES);

		if (property == null) {

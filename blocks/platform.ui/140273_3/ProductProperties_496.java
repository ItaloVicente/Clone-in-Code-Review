		return getImages(property, product.getDefiningBundle());
	}

	public static URL getWelcomePageUrl(IProduct product) {
		return getUrl(product.getProperty(WELCOME_PAGE), product.getDefiningBundle());
	}

	public static String getProductName(IProduct product) {
		return product.getName();
	}

	public static String getProductId(IProduct product) {
		return product.getId();
	}

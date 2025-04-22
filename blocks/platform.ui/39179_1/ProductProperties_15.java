		return productId;
	}

	public static String getAppName(IProduct product) {
		String property = product.getProperty(APP_NAME);
		if (property == null) {

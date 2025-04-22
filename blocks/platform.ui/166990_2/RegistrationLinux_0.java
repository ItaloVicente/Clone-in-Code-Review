		this(new FileProvider(), new ProcessExecutor(), getProductName());
	}

	private static String getProductName() {
		IProduct product = Platform.getProduct();
		String name = product == null ? DEFAULT_PRODUCT_NAME : product.getName();
		return name == null ? DEFAULT_PRODUCT_NAME : name;

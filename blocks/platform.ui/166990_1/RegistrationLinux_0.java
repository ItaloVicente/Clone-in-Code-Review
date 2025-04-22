		this(new FileProvider(), new ProcessExecutor(), getProductName());
	}

	private static String getProductName() {
		String name = Platform.getProduct().getName();
		return name == null ? DEFAULT_PRODUCT_NAME : name;

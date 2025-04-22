	private String getProductName() {
		IProduct product = Platform.getProduct();
		String name = product == null ? null : product.getName();
		return name == null ? UIText.GitPreferenceRoot_DefaultProductName
				: name;
	}


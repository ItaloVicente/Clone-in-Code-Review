		URL productUrl = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			productUrl = ProductProperties.getWelcomePageUrl(product);
			welcomeFeatures.add(new AboutInfo(product));
		}

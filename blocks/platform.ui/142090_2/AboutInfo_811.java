		return id != null ? id : ""; //$NON-NLS-1$
	}

	public String getAboutText() {
		return productProperties == null ? null : productProperties
				.getAboutText();
	}

	public String getAppName() {
		return productProperties == null ? null : productProperties
				.getAppName();
	}

	public String getProductName() {
		return productProperties == null ? null : productProperties
				.getProductName();
	}

	public String getProviderName() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getProviderName();
	}

	public String getVersionId() {
		return bundleGroupProperties == null ? "" : bundleGroupProperties.getFeatureVersion(); //$NON-NLS-1$
	}

	public URL getWelcomePageURL() {
		if (productProperties != null) {

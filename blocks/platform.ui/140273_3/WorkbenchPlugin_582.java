	public Bundle[] getBundles() {
		return bundleContext == null ? new Bundle[0] : bundleContext.getBundles();
	}

	public BundleContext getBundleContext() {
		return bundleContext;
	}

	public String getAppName() {
		return getProductInfo().getAppName();
	}

	public String getProductName() {
		return getProductInfo().getProductName();
	}

	public ImageDescriptor[] getWindowImages() {
		return getProductInfo().getWindowImages();
	}

	private ProductInfo getProductInfo() {
		if (productInfo == null) {

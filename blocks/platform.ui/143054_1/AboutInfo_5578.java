	private ProductProperties productProperties;

	private BundleGroupProperties bundleGroupProperties;

	private Long featureImageCRC;

	private boolean calculatedImageCRC = false;

	public AboutInfo(IProduct product) {
		this.productProperties = new ProductProperties(product);
	}

	public AboutInfo(IBundleGroup bundleGroup) {
		this.bundleGroupProperties = new BundleGroupProperties(bundleGroup);
	}

	public static AboutInfo readFeatureInfo(String featureId, String versionId) {
		Assert.isNotNull(featureId);
		Assert.isNotNull(versionId);

		IProduct product = Platform.getProduct();
		if (product != null
				&& featureId.equals(ProductProperties.getProductId(product))) {

    private ProductProperties productProperties;

    private BundleGroupProperties bundleGroupProperties;

    private Long featureImageCRC;

    private boolean calculatedImageCRC = false;

    /**
     * The information contained in this info will apply to only the argument product.
     */
    public AboutInfo(IProduct product) {
        this.productProperties = new ProductProperties(product);
    }

    /**
     * This info object will apply to the argument bundle group.
     */
    public AboutInfo(IBundleGroup bundleGroup) {
        this.bundleGroupProperties = new BundleGroupProperties(bundleGroup);
    }

    /**
     * Returns the configuration information for the feature with the given id.
     *
     * @param featureId
     *            the feature id
     * @param versionId
     *            the version id (of the feature)
     * @return the configuration information for the feature
     */
    public static AboutInfo readFeatureInfo(String featureId, String versionId) {
        Assert.isNotNull(featureId);
        Assert.isNotNull(versionId);

        IProduct product = Platform.getProduct();
        if (product != null
                && featureId.equals(ProductProperties.getProductId(product))) {

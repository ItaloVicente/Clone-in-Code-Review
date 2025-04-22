    static void registerBucketForRefresh(final Map<LoaderType, Refresher> refreshers, final LoaderType loaderType,
                                         final BucketConfig config) {
        Refresher refresher;

        boolean loadedFromCarrier = loaderType == LoaderType.Carrier;
        boolean canFetchFromCarrier = config instanceof CouchbaseBucketConfig
            && config.capabilities().contains(BucketCapabilities.NODES_EXT);

        LOGGER.debug("Loaded from loader {}, can fetch from carrier {}", loaderType, canFetchFromCarrier);
        if (loadedFromCarrier || canFetchFromCarrier) {
            refresher = refreshers.get(LoaderType.Carrier);
        } else {
            refresher = refreshers.get(LoaderType.HTTP);

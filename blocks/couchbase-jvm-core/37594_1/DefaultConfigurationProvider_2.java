        this(
            cluster,
            environment,
            Arrays.asList((Loader) new CarrierLoader(cluster, environment), new HttpLoader(cluster, environment))
        );
    }

    public DefaultConfigurationProvider(final Cluster cluster, final Environment environment,
        final List<Loader> loaderChain) {
        if (cluster == null) {
            throw new IllegalArgumentException("A cluster reference needs to be provided");
        }
        if (loaderChain == null || loaderChain.isEmpty()) {
            throw new IllegalArgumentException("At least one config loader needs to be provided");
        }

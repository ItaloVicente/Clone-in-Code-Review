                                final String password, final List<Transcoder<? extends Document, ?>> customTranscoders) {
        this(core, environment, name, name, password, customTranscoders);
    }

    public CouchbaseAsyncBucket(final ClusterFacade core, final CouchbaseEnvironment environment, final String name,
                                final String username, final String password, final List<Transcoder<? extends Document, ?>> customTranscoders) {

        return new NodeLocatorHelper(bucket.core(), bucket.name());
    }

    public static Single<NodeLocatorHelper> create(final AsyncBucket bucket) {
        return bucket.core().map(
                new Func1<ClusterFacade, NodeLocatorHelper>() {
                    @Override
                    public NodeLocatorHelper call(ClusterFacade core) {
                        return new NodeLocatorHelper(core, bucket.name());
                    }
                }
            ).toSingle();

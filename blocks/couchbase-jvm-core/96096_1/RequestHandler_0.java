    private boolean shouldAddService(final ServiceType serviceType, final NetworkAddress hostname,
        final BucketConfig config) {
        if (serviceType.equals(ServiceType.BINARY) && config instanceof CouchbaseBucketConfig) {
            return ((CouchbaseBucketConfig) config).hasPrimaryPartitionsOnNode(hostname);
        } else {
            return true;
        }
    }


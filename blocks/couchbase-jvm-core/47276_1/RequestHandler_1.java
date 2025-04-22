    protected void checkFeaturesForRequest(CouchbaseRequest request, BucketConfig config) {
        if (request instanceof BinaryRequest && !config.serviceEnabled(ServiceType.BINARY)) {
            throw new ServiceNotAvailableException("The KeyValue service is not enabled or no node in the cluster supports it.");
        } else if (request instanceof ViewRequest && !config.serviceEnabled(ServiceType.VIEW)) {
            throw new ServiceNotAvailableException("The View service is not enabled or no node in the cluster supports it.");
        } else if (request instanceof QueryRequest && !(environment.queryEnabled() || config.serviceEnabled(ServiceType.QUERY))) {
            throw new ServiceNotAvailableException("The Query service is not enabled or no node in the cluster supports it.");
        } else if (request instanceof  DCPRequest && !(environment.dcpEnabled() || config.serviceEnabled(ServiceType.DCP))) {
            throw new ServiceNotAvailableException("The DCP service is not enabled or no node in the cluster supports it.");

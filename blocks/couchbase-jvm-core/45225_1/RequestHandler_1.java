    protected void checkFeaturesForRequest(CouchbaseRequest request) {
        if (request instanceof QueryRequest && !environment.queryEnabled()) {
            throw new UnsupportedOperationException("Request type needs a feature to be enabled in environment: query");
        } else if (request instanceof  DCPRequest && !environment.dcpEnabled()) {
            throw new UnsupportedOperationException("Request type needs a feature to be enabled in environment: dcp");
        }
    }


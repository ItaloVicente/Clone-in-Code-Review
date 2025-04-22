
    @Override
    protected CouchbaseRequest createKeepAliveRequest() {
        return new KeepAliveRequest();
    }

    protected static class KeepAliveRequest extends AbstractCouchbaseRequest implements ViewRequest {
        protected KeepAliveRequest() {
            super(null, null);
        }
    }

    protected static class KeepAliveResponse extends AbstractCouchbaseResponse {
        protected KeepAliveResponse(ResponseStatus status, CouchbaseRequest request) {
            super(status, request);
        }
    }

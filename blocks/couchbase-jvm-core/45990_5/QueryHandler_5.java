    @Override
    protected CouchbaseRequest createKeepAliveRequest() {
        return new KeepAliveRequest();
    }

    protected static class KeepAliveRequest extends AbstractCouchbaseRequest implements QueryRequest {
        protected KeepAliveRequest() {
            super(null, null);
        }
    }

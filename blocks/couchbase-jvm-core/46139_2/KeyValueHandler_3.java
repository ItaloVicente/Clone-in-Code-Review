    @Override
    protected CouchbaseRequest createKeepAliveRequest() {
        return new KeepAliveRequest();
    }

    protected static class KeepAliveRequest extends AbstractKeyValueRequest {

        protected KeepAliveRequest() {
            super(null, null, null);
            partition((short) 0);
        }
    }

    protected static class KeepAliveResponse extends AbstractKeyValueResponse {

        public KeepAliveResponse(ResponseStatus status, CouchbaseRequest request) {
            super(status, null, null, request);
        }
    }

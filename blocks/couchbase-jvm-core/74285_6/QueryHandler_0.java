        createParser(false);
    }

    protected QueryHandler(AbstractEndpoint endpoint, RingBuffer<ResponseEvent> responseBuffer, Queue<QueryRequest> queue,
                 boolean isTransient, final boolean pipeline, boolean enableV2) {
        super(endpoint, responseBuffer, queue, isTransient, pipeline);
        createParser(enableV2);
    }

    private void createParser(boolean enableV2) {
        boolean v2EnabledBySystemProperty = System.getProperty("com.couchbase.enableNewQueryResponseParser") != null;
        if (v2EnabledBySystemProperty || enableV2) {
            parser = new QueryResponseParserV2(env().scheduler(), env().autoreleaseAfter());
        } else {
            parser = new QueryResponseParserV1(env().scheduler(), env().autoreleaseAfter());
        }

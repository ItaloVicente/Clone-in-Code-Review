    private static final CouchbaseCoreSendHook DEFAULT_CORE_HOOK = new CouchbaseCoreSendHook() {
        @Override
        public Tuple2<CouchbaseRequest, Subject<CouchbaseResponse, CouchbaseResponse>> beforeSend(CouchbaseRequest originalRequest, Subject<CouchbaseResponse, CouchbaseResponse> originalResponse) {
            return Tuple.create(originalRequest, originalResponse);
        }
    };


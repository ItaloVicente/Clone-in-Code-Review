    private static final CouchbaseCoreSendHook DEFAULT_CORE_HOOK = new CouchbaseCoreSendHook() {
        @Override
        public Tuple2<CouchbaseRequest, Observable<CouchbaseResponse>> beforeSend(CouchbaseRequest originalRequest, Observable<CouchbaseResponse> originalResponse) {
            return Tuple.create(originalRequest, originalResponse);
        }
    };


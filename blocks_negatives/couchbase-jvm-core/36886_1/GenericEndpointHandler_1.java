    /**
     * Translates {@link CouchbaseRequest}s into {@link RequestEvent}s.
     */
    private static final EventTranslatorTwoArg<ResponseEvent, CouchbaseResponse, Subject<CouchbaseResponse, CouchbaseResponse>> RESPONSE_TRANSLATOR =
        new EventTranslatorTwoArg<ResponseEvent, CouchbaseResponse, Subject<CouchbaseResponse, CouchbaseResponse>>() {
            @Override
            public void translateTo(ResponseEvent event, long sequence, CouchbaseResponse response, Subject<CouchbaseResponse, CouchbaseResponse> observable) {
                event.setResponse(response);
                event.setObservable(observable);
            }
        };


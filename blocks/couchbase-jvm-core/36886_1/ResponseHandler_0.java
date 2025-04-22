    private final Cluster cluster;

    public ResponseHandler(Cluster cluster) {
        this.cluster = cluster;
    }

    public static final EventTranslatorTwoArg<ResponseEvent, CouchbaseMessage, Subject<CouchbaseResponse, CouchbaseResponse>> RESPONSE_TRANSLATOR =
        new EventTranslatorTwoArg<ResponseEvent, CouchbaseMessage, Subject<CouchbaseResponse, CouchbaseResponse>>() {
            @Override
            public void translateTo(ResponseEvent event, long sequence, CouchbaseMessage message, Subject<CouchbaseResponse, CouchbaseResponse> observable) {
                event.setMessage(message);
                event.setObservable(observable);
            }
        };


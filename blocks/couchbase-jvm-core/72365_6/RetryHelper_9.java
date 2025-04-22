
    public static void retry(final CouchbaseRequest request, final EventSink<ResponseEvent> responseBuffer) {
        if(!responseBuffer.tryPublishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable())) {
            request.observable().onError(CouchbaseCore.BACKPRESSURE_EXCEPTION);
        }
    }

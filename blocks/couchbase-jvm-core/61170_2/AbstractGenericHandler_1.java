    private static void completeResponse(final CouchbaseResponse response,
        final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        try {
            observable.onNext(response);
            observable.onCompleted();
        } catch (Exception ex) {
            LOGGER.warn("Caught exception while onNext on observable", ex);
            observable.onError(ex);
        }
    }


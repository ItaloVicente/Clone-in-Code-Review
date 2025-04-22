    protected void publishResponse(final CouchbaseResponse response,
        final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, response, observable);
    }

    protected void finishedDecoding() {
        this.currentDecodingState = DecodingState.FINISHED;
    }


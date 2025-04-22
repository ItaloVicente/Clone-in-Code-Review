    private void retryOrCancel(final CouchbaseRequest request) {
        if (env.retryPolicy() == RetryPolicy.BEST_EFFORT) {
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
        } else {
            request.observable().onError(new RequestCancelledException("Could not dispatch request to a "
                    + "connected node."));
        }
    }


        try {
            CouchbaseResponse response = decodeResponse(ctx, msg);
            if (response != null) {
                publishResponse(response, currentRequest.observable());
            }
        } catch (CouchbaseException e) {
            currentRequest.observable().onError(e);
        } catch (Exception e) {
            currentRequest.observable().onError(new CouchbaseException(e));

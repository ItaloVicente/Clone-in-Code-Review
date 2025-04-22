        if (request instanceof StatRequest) {
            if (((StatResponse) response).key() == null) {
                finishedDecoding();
                if (response.status().isSuccess()) {
                    request.observable().onCompleted();
                    return null;
                } else {
                    return response;
                }
            }
        } else {
            finishedDecoding();
        }

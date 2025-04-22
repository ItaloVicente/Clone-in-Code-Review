        if (request instanceof StatRequest) {
            if (((StatResponse) response).key() == null) {
                if (((StatRequest) request).finish()) {
                    request.observable().onCompleted();
                }
                finishedDecoding();
                return null;
            }
        } else {
            finishedDecoding();
        }

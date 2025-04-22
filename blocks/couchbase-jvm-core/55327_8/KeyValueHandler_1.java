        if (request instanceof StatRequest) {
            if (((StatResponse) response).key() == null) {
                    ((StatRequest) request).onCompleted();
                finishedDecoding();
                return null;
            }
        } else {
            finishedDecoding();
        }

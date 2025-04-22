        if (request instanceof StatRequest) {
            ((StatRequest)request).add((StatResponse) response);
            if (((StatResponse) response).key() == null) {
                finishedDecoding();
            }
            return null;
        } else {
            finishedDecoding();
        }

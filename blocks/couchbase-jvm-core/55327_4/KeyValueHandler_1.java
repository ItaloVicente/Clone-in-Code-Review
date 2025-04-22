        if (request instanceof StatRequest) {
            if (((StatResponse)response).key() == null) {
                finishedDecoding();
            }
        } else {
            finishedDecoding();
        }

        ENCODED request;
        try {
            request = encodeRequest(ctx, msg);
        } catch (Exception ex) {
            msg.observable().onError(new RequestCancelledException("Error while encoding Request, cancelling.", ex));
            throw ex;
        }

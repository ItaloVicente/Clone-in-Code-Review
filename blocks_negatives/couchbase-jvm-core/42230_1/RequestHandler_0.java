        if (!(request instanceof GetBucketConfigRequest || request instanceof BucketConfigRequest)
                && request.bucket() != null
                && !configuration.get().hasBucket(request.bucket())) {
            request.observable().onError(new BucketClosedException(request.bucket() + " has been closed"));
            event.setRequest(null);
            return;

        BucketConfig bucketConfig = config.bucketConfig(request.bucket());
        if (!(bucketConfig instanceof CouchbaseBucketConfig)) {
            request.observable().onError(NOT_AVAILABLE);
            return null;
        }


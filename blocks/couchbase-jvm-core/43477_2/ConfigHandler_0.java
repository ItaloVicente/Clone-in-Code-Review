        return new BucketStreamingResponse(
            scheduledObservable,
            host,
            status,
            currentRequest()
        );

        return core.<CloseBucketResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new CloseBucketRequest(bucket);
                }
            })

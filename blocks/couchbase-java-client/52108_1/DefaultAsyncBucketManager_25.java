            .<BucketConfigResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new BucketConfigRequest("/pools/default/buckets/", null, bucket, password);
                }
            })

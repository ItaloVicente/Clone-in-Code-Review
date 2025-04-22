            .<BucketConfigResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new BucketConfigRequest(TERSE_PATH, hostname, bucket, password);
                }
            })

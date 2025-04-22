        return core.<GetResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new GetRequest(id, bucket, false, true, expiry);
                }
            })

            .<FlushResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new FlushRequest(bucket, password);
                }
            })

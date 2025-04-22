            .send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new OpenBucketRequest(name, password);
                }
            })

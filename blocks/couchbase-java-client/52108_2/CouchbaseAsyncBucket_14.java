            .<UnlockResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new UnlockRequest(id, cas, bucket);
                }
            })

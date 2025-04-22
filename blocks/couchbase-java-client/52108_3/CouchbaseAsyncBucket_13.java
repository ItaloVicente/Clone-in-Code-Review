            .<CounterResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new CounterRequest(id, initial, delta, expiry, bucket);
                }
            })

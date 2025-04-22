            .<ObserveResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new ObserveRequest(id, 0, true, (short) 0, bucket);
                }
            })

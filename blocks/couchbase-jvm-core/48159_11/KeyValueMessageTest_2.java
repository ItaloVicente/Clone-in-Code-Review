        GetResponse getResponse = cluster().<GetResponse>send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return new GetRequest(key, bucket());
            }
        }).toBlocking().single();

            .<DisconnectResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new DisconnectRequest();
                }
            })

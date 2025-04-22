            .<GetConfigProviderResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new GetConfigProviderRequest();
                }
            })

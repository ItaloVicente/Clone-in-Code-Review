        core.send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return new SeedNodesRequest(Collections.singletonList(TestProperties.seedNode()));
            }
        }).toBlocking().single();

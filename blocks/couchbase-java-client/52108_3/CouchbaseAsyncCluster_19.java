        core.send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return new SeedNodesRequest(assembleSeedNodes(connectionString, environment));
            }
        }).toBlocking().single();

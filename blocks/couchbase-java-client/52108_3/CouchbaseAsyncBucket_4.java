                .<GetClusterConfigResponse>send(new RequestFactory() {
                    @Override
                    public CouchbaseRequest call() {
                        return new GetClusterConfigRequest();
                    }
                })

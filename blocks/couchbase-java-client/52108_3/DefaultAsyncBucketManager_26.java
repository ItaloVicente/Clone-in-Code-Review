        return core.<GetDesignDocumentsResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new GetDesignDocumentsRequest(bucket, password);
                }
            })

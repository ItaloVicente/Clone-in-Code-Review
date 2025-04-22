            .<GenericQueryResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return GenericQueryRequest.jsonQuery(query, bucket, password);
                }
            })

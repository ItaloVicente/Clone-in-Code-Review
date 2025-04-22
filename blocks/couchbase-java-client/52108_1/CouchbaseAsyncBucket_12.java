            .<GenericQueryResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return GenericQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password);
                }
            })

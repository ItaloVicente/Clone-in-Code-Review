            .<RemoveResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new RemoveRequest(document.id(), document.cas(), bucket);
                }
            })

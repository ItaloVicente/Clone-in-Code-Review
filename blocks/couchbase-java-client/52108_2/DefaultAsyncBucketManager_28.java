        return core.<UpsertDesignDocumentResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new UpsertDesignDocumentRequest(designDocument.name(),
                        body, development, bucket, password);
                }
            })

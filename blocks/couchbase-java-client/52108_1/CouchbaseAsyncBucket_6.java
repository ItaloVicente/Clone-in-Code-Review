            incoming = core.send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new ReplicaGetRequest(id, bucket, (short) type.ordinal());
                }
            });

        return core.send(new OpenBucketRequest(name, password)).map(new Func1<CouchbaseResponse, Bucket>() {
        @Override
        public Bucket call(CouchbaseResponse response) {
            return new CouchbaseBucket(core, name, password);
        }
        });

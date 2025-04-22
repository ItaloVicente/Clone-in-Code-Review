    public Observable<Bucket> openBucket(final String name, String pass,
        final List<Transcoder<? extends Document, ?>> transcoders) {
        final String password = pass == null ? "" : pass;

        final List<Transcoder<? extends Document, ?>> trans = transcoders == null
            ? new ArrayList<Transcoder<? extends Document, ?>>() : transcoders;
        return core
            .send(new OpenBucketRequest(name, password))
            .map(new Func1<CouchbaseResponse, Bucket>() {

        return openBucket(name, pass, null);
    }

    @Override
    public Observable<Bucket> openBucket(final String name, String pass,
        final List<Transcoder<? extends Document, ?>> transcoders) {

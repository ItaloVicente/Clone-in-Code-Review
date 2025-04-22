
    @Override
    public AsPath fromCurrentBucket() {
        element(new FromElement(CouchbaseAsyncBucket.CURRENT_BUCKET_IDENTIFIER));
        return new DefaultAsPath(this);
    }

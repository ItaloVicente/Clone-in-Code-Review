                }).map(new Func1<UpdateBucketResponse, BucketSettings>() {
                    @Override
                    public BucketSettings call(UpdateBucketResponse response) {
                        if (!response.status().isSuccess()) {
                            throw new CouchbaseException("Could not update bucket: " + response.config());
                        }
                        return settings;

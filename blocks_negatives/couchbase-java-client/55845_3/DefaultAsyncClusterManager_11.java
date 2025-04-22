                }
            }).flatMap(new Func1<Boolean, Observable<UpdateBucketResponse>>() {
                @Override
                public Observable<UpdateBucketResponse> call(Boolean exists) {
                    return core.send(new UpdateBucketRequest(settings.name(), sb.toString(), username, password));
                }
            }).map(new Func1<UpdateBucketResponse, BucketSettings>() {
                @Override
                public BucketSettings call(UpdateBucketResponse response) {
                    if (!response.status().isSuccess()) {
                        throw new CouchbaseException("Could not update bucket: " + response.config());

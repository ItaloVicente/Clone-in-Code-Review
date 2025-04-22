        return core
            .<BucketConfigResponse>send(new BucketConfigRequest("/pools/default/buckets/", null, bucket, password))
            .map(new Func1<BucketConfigResponse, BucketInfo>() {
                @Override
                public BucketInfo call(BucketConfigResponse response) {
                    try {
                        return DefaultBucketInfo.create(
                            CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(response.config())
                        );
                    } catch (Exception ex) {
                        throw new TranscodingException("Could not decode bucket info.", ex);
                    }

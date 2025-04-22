                .bucketManager()
                .map(new Func1<AsyncBucketManager, BucketManager>() {
                    @Override
                    public BucketManager call(AsyncBucketManager asyncBucketManager) {
                        return DefaultBucketManager.create(environment, name, password, core);
                    }
                })
                .toBlocking()
                .single();

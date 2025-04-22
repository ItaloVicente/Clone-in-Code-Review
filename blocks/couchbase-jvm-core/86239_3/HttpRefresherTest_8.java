        refresher.configs()
            .map(new Func1<String, BucketConfig>() {
                @Override
                public BucketConfig call(String s) {
                    return BucketConfigParser.parse(s, mock(CoreEnvironment.class));
                }
            })
            .subscribe(new Action1<BucketConfig>() {
                @Override
                public void call(BucketConfig bucketConfig) {
                    assertEquals("default", bucketConfig.name());
                    latch.countDown();
                }
            });

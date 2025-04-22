        refresher.configs().subscribe(new Action1<BucketConfig>() {
            @Override
            public void call(BucketConfig bucketConfig) {
                assertEquals("default", bucketConfig.name());
                latch.countDown();
            }
        });

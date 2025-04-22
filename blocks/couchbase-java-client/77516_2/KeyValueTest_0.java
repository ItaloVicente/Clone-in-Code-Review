    @Test
    public void shouldNotSendTimedOutSyncOperations() {
        String key = "TimedOutSync";
        int incrementCount = 6000;
        bucket().upsert(JsonLongDocument.create(key, 0L));
        for (int i=0; i< incrementCount; i++) {
            try {
                bucket().counter(key, 1, 0, TimeUnit.SECONDS);
            } catch(RuntimeException ex) {
            }
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
        }
        assert(bucket().get(key, JsonLongDocument.class).content() < incrementCount);
    }

    @Test
    public void shouldNotSendTimedOutAsyncOperations() {
        String key = "TimedOutAsync";
        int incrementCount = 6000;
        bucket().upsert(JsonLongDocument.create(key, 0L));
        final CountDownLatch latch = new CountDownLatch(incrementCount);
        for (int i=0; i< incrementCount; i++) {
            try {
                bucket().async().counter(key, 1)
                        .timeout(0, TimeUnit.SECONDS)
                        .subscribe(
                            new Action1<JsonLongDocument>() {
                                @Override
                                public void call(JsonLongDocument doc) {
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable err) {
                                   latch.countDown();
                                }
                            }, new Action0() {
                                @Override
                                public void call() {
                                    latch.countDown();
                                }
                            });
            } catch(RuntimeException ex) {
            }
        }
        try {
            latch.await();
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
        }
        assert(bucket().get(key, JsonLongDocument.class).content() < incrementCount);
    }


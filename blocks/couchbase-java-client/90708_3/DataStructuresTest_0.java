    @Test
    public void testMultiThreadQueuePop() throws Exception {
        ctx.bucket().queuePush("testMultiThreadQueuePop", 1, MutationOptionBuilder.builder().createDocument(true));

        final CountDownLatch latch = new CountDownLatch(10);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    ctx.bucket().async().queuePop("testMultiThreadQueuePop", Integer.class)
                            .subscribe(
                                    new Action1<Integer>() {
                                        @Override
                                        public void call(Integer val) {
                                            if (val == 1) {
                                                atomicInteger.incrementAndGet();
                                            }
                                        }
                                    },
                                    new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            latch.countDown();
                                        }
                                    },
                                    new Action0() {
                                        @Override
                                        public void call() {
                                            latch.countDown();
                                        }
                                    }

                            );
                }
            });
        }
        latch.await();
        Assert.assertEquals(1, atomicInteger.get());
    }

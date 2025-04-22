
        @Test
    public void shouldCallDoOnRetryBeforeEachRetryUntilExceptionNotRetriable() {
        TestScheduler testScheduler = new TestScheduler();
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();
        final LinkedList<String> retryLog = new LinkedList<String>();

        RetryWithDelayHandler timeHandler = new RetryWithDelayHandler(
                MAX_ATTEMPTS,
                Delay.linear(TimeUnit.SECONDS),
                new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return throwable instanceof IllegalArgumentException;
                    }
                },
                new RetryBuilder.OnRetryAction() {
                    @Override
                    public void call(Integer attempt, Throwable retriedError, Long delay, TimeUnit timeUnit) {
                        String logLine = "Retry #" + attempt + " in " + delay + " " + timeUnit +
                            " for " + retriedError;
                        retryLog.add(logLine);
                    }
                }, testScheduler);

        final AtomicLong step = new AtomicLong(0);
        Observable<String> erroring = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (step.getAndIncrement() < 3)
                    subscriber.onError(new IllegalStateException());
                else
                    subscriber.onError(new IllegalArgumentException());
            }
        });
        erroring.retryWhen(new RetryWhenFunction(timeHandler), testScheduler).subscribe(testSubscriber);

        assertEquals(0, retryLog.size());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertEquals(1, retryLog.size());
        assertEquals("Retry #1 in 1 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(2, retryLog.size());
        assertEquals("Retry #2 in 2 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, retryLog.size());
        assertEquals("Retry #3 in 3 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        assertEquals(3, retryLog.size());
        testSubscriber.assertNoValues();
        testSubscriber.assertError(IllegalArgumentException.class);
    }

    @Test
    public void shouldCallDoOnRetryBeforeEachRetryUntilMaxAttempt() {
        TestScheduler testScheduler = new TestScheduler();
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();
        final LinkedList<String> retryLog = new LinkedList<String>();

        RetryWithDelayHandler timeHandler = new RetryWithDelayHandler(
                3,
                Delay.linear(TimeUnit.SECONDS),
                new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return throwable instanceof OutOfMemoryError;
                    }
                },
                new RetryBuilder.OnRetryAction() {
                    @Override
                    public void call(Integer attempt, Throwable retriedError, Long delay, TimeUnit timeUnit) {
                        String logLine = "Retry #" + attempt + " in " + delay + " " + timeUnit +
                            " for " + retriedError;
                        retryLog.add(logLine);
                    }
                }, testScheduler);

        Observable<String> erroring = Observable.error(new IllegalStateException());
        erroring.retryWhen(new RetryWhenFunction(timeHandler), testScheduler)
                .doOnError(new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   System.out.println(throwable);
                               }
                           }
                )
                .subscribe(testSubscriber);

        assertEquals(0, retryLog.size());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertEquals(1, retryLog.size());
        assertEquals("Retry #1 in 1 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(2, retryLog.size());
        assertEquals("Retry #2 in 2 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, retryLog.size());
        assertEquals("Retry #3 in 3 SECONDS for java.lang.IllegalStateException", retryLog.getLast());
        testSubscriber.assertNoValues();
        testSubscriber.assertNoTerminalEvent();

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        assertEquals(3, retryLog.size());
        testSubscriber.assertNoValues();
        testSubscriber.assertError(CannotRetryException.class);
    }

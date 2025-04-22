    @Test
    public void testTraceIdAppearsWhenMultiSubscribers() throws Exception {
        UnicastAutoReleaseSubject<Object> subject = UnicastAutoReleaseSubject.createWithoutNoSubscriptionTimeout();
        subject.withTraceIdentifier("multiSub");

        subject.subscribe(Subscribers.empty());
        try {
            subject.toBlocking().last();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e).hasMessageStartingWith("This Observable (multiSub)");
        }
    }

    @Test
    public void testTraceIdAppearsWhenNoSubscription() throws Exception {
        TestScheduler testScheduler = Schedulers.test();
        OnUnsubscribeAction onUnsub = new OnUnsubscribeAction();
        UnicastAutoReleaseSubject<String> subject = UnicastAutoReleaseSubject.create(1, TimeUnit.DAYS, testScheduler,
                onUnsub);
        subject.withTraceIdentifier("noSub");
        subject.onNext("Start the timeout now."); // Since the timeout is scheduled only after content arrival.
        testScheduler.advanceTimeBy(1, TimeUnit.DAYS);
        try {
            subject.toBlocking().last(); // Should immediately throw an error.
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertThat(e).hasMessageStartingWith("The content of this Observable (noSub)");
        }
    }


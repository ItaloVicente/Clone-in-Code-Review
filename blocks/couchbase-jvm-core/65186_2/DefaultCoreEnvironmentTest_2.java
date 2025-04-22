    @Test
    public void shouldEmitEvent() {
        CoreEnvironment env = DefaultCoreEnvironment.create();

        final AtomicInteger evtCount = new AtomicInteger(0);
        env.eventBus().get().forEach(new Action1<CouchbaseEvent>() {
            @Override
            public void call(CouchbaseEvent couchbaseEvent) {
                if (couchbaseEvent instanceof TooManyEnvironmentsEvent) {
                    evtCount.set(((TooManyEnvironmentsEvent) couchbaseEvent).numEnvs());
                }
            }
        });

        CoreEnvironment env2 = DefaultCoreEnvironment.builder().eventBus(env.eventBus()).build();

        env.shutdown();
        env2.shutdown();

        assertTrue(evtCount.get() > 1);
    }


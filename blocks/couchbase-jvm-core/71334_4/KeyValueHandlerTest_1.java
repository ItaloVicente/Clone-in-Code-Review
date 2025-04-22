    private static final CoreEnvironment ENVIRONMENT;

    static {
        ENVIRONMENT = mock(CoreEnvironment.class);
        when(ENVIRONMENT.scheduler()).thenReturn(Schedulers.computation());
    }


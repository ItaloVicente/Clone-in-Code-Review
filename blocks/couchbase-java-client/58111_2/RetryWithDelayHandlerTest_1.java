
    @Test
    public void shouldLimitMaxAttemptsToIntegerMaxValueMinusOne() {
        int normal = 4000;
        int underLimit = Integer.MAX_VALUE - 1;
        int atLimit = Integer.MAX_VALUE;

        RetryWithDelayHandler testNormal = new RetryWithDelayHandler(normal, Retry.DEFAULT_DELAY, null, null);
        RetryWithDelayHandler testUnderLimit = new RetryWithDelayHandler(underLimit, Retry.DEFAULT_DELAY, null, null);
        RetryWithDelayHandler testAtLimit = new RetryWithDelayHandler(atLimit, Retry.DEFAULT_DELAY, null, null);

        assertEquals(normal, testNormal.maxAttempts);
        assertEquals(underLimit, testUnderLimit.maxAttempts);
        assertEquals(atLimit - 1, testAtLimit.maxAttempts);
    }

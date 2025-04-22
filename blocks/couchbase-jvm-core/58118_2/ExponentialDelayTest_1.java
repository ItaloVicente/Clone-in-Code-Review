    @Test
    public void shouldNotOverflowIn100Retries() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 2d);

        long previous = Long.MIN_VALUE;
        for(int i = 0; i < 100; i++) {
            long now = exponentialDelay.calculate(i);
            assertTrue("delay is at " + now + " down from " + previous + ", attempt " + i, now >= previous);
            previous = now;
        }
    }


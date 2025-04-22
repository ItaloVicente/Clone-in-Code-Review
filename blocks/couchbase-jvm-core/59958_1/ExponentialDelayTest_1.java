    @Test
    public void testPowerOfTenShouldCalculateExponentially() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 1, 10);

        assertEquals(0, exponentialDelay.calculate(0));
        assertEquals(1, exponentialDelay.calculate(1)); //1 * 10^0
        assertEquals(10, exponentialDelay.calculate(2)); //1 * 10^1
        assertEquals(100, exponentialDelay.calculate(3)); //1 * 10^2
        assertEquals(1000, exponentialDelay.calculate(4));
        assertEquals(10000, exponentialDelay.calculate(5));
        assertEquals(100000, exponentialDelay.calculate(6));

        assertEquals("ExponentialDelay{growBy 1.0 SECONDS, powers of 10; lower=0, upper=2147483647}", exponentialDelay.toString());
    }

    @Test
    public void testPowerOfTenShouldRespectLowerBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 400, 1, 10);

        assertEquals(400, exponentialDelay.calculate(0));
        assertEquals(400, exponentialDelay.calculate(1));
        assertEquals(400, exponentialDelay.calculate(2));
        assertEquals(400, exponentialDelay.calculate(3));
        assertEquals(1000, exponentialDelay.calculate(4));
        assertEquals(10000, exponentialDelay.calculate(5));
        assertEquals(100000, exponentialDelay.calculate(6));

        assertEquals("ExponentialDelay{growBy 1.0 SECONDS, powers of 10; lower=400, upper=2147483647}", exponentialDelay.toString());
    }

    @Test
    public void testPowerOfTenShouldRespectUpperBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, 500, 0, 1, 10);

        assertEquals(0, exponentialDelay.calculate(0));
        assertEquals(1, exponentialDelay.calculate(1));
        assertEquals(10, exponentialDelay.calculate(2));
        assertEquals(100, exponentialDelay.calculate(3));
        assertEquals(500, exponentialDelay.calculate(4));
        assertEquals(500, exponentialDelay.calculate(5));
        assertEquals(500, exponentialDelay.calculate(6));

        assertEquals("ExponentialDelay{growBy 1.0 SECONDS, powers of 10; lower=0, upper=500}", exponentialDelay.toString());
    }

    @Test
    public void testPowerOfTenShouldApplyFactor() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 2, 10);

        assertEquals(0, exponentialDelay.calculate(0));
        assertEquals(2, exponentialDelay.calculate(1));
        assertEquals(20, exponentialDelay.calculate(2));
        assertEquals(200, exponentialDelay.calculate(3));
        assertEquals(2000, exponentialDelay.calculate(4));
        assertEquals(20000, exponentialDelay.calculate(5));
        assertEquals(200000, exponentialDelay.calculate(6));

        assertEquals("ExponentialDelay{growBy 2.0 SECONDS, powers of 10; lower=0, upper=2147483647}", exponentialDelay.toString());
    }

    @Test
    public void testPowerOfTenShouldNotOverflowInAMillionRetries() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Long.MAX_VALUE, 0, 2d, 10);

        long previous = Long.MIN_VALUE;
        for(int i = 0; i < 1000000; i++) {
            long now = exponentialDelay.calculate(i);
            assertTrue("delay is at " + now + " down from " + previous + ", attempt " + i, now >= previous);
            previous = now;
        }
    }

    @Test
    public void testAlternatePowerAndBitshiftPowerProduceSameResult() {
        ExponentialDelay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Long.MAX_VALUE, 0, 1, 2);

        for(int i = 1; i < 1000000; i++) {
            long powValue = exponentialDelay.calculateAlternatePower(i);
            long bitshiftValue = exponentialDelay.calculatePowerOfTwo(i);

            assertEquals("difference at step " + i, powValue, bitshiftValue);
        }
    }


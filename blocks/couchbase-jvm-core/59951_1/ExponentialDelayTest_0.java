        assertEquals(0, exponentialDelay.calculate(0));
        assertEquals(10, exponentialDelay.calculate(1));
        assertEquals(20, exponentialDelay.calculate(2));
        assertEquals(40, exponentialDelay.calculate(3));
        assertEquals(80, exponentialDelay.calculate(4));
        assertEquals(160, exponentialDelay.calculate(5));
        assertEquals(320, exponentialDelay.calculate(6));

        assertEquals(2, exponentialDelay.calculate(1));
        assertEquals(4, exponentialDelay.calculate(2));
        assertEquals(8, exponentialDelay.calculate(3));
        assertEquals(16, exponentialDelay.calculate(4));
        assertEquals(32, exponentialDelay.calculate(5));
        assertEquals(64, exponentialDelay.calculate(6));

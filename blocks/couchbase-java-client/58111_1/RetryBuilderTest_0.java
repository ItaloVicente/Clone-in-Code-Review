    @Test
    public void testRetryMaxIsCappedAtIntegerMaxValueMinusOne() {
        RetryWhenFunction result = RetryBuilder.any().max(Integer.MAX_VALUE).build();

        assertEquals(Integer.MAX_VALUE - 1, result.handler.maxAttempts);
    }


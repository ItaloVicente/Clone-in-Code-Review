
    @Test
    public void testErrorPredicateIsInverted() {
        Func1<Throwable, Boolean> errorRetriesPredicate = new Func1<Throwable, Boolean>() {
            @Override
            public Boolean call(Throwable throwable) {
                return "toto".equals(throwable.getMessage());
            }
        };
        RetryWhenFunction fun = RetryBuilder.anyMatches(errorRetriesPredicate).build();

        RuntimeException retryException = new RuntimeException("toto");
        RuntimeException stopException = new RuntimeException("abc");

        assertTrue(errorRetriesPredicate.call(retryException));
        assertFalse(errorRetriesPredicate.call(stopException));

        assertTrue(fun.handler.errorInterruptingPredicate instanceof RetryBuilder.InversePredicate);
        assertNotEquals(errorRetriesPredicate.call(retryException), fun.handler.errorInterruptingPredicate.call(retryException));
        assertNotEquals(errorRetriesPredicate.call(stopException), fun.handler.errorInterruptingPredicate.call(stopException));
    }

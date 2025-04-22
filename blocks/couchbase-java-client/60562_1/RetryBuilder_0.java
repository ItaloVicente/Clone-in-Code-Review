    public static RetryBuilder anyMatches(Func1<Throwable, Boolean> retryErrorPredicate) {
        RetryBuilder retryBuilder = new RetryBuilder();
        retryBuilder.maxAttempts = 1;

        retryBuilder.retryErrorPredicate = retryErrorPredicate;
        return retryBuilder;
    }


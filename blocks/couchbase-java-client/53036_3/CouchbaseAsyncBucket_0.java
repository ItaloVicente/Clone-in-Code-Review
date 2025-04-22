        })
        .retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer attempts, Throwable throwable) {
                int maxAttempts = 1;
                if (environment().retryStrategy() == BestEffortRetryStrategy.INSTANCE) {
                    maxAttempts = 100;
                }
                if (attempts <= maxAttempts && throwable instanceof NamedPreparedStatementException) {
                    LOGGER.warn("Retrying #" + attempts + " prepared query " + query.n1ql());
                    return true;
                }
                return false;
            }

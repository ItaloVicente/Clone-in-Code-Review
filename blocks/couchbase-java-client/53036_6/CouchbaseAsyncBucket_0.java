        })
        .retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer attempts, Throwable throwable) {
                if (throwable instanceof NamedPreparedStatementException
                        && (attempts == 1 || environment.retryStrategy().shouldRetry(request, environment()))){
                    LOGGER.warn("Retrying #" + attempts + " prepared query " + query.n1ql());
                    return true;
                }
                return false;
            }

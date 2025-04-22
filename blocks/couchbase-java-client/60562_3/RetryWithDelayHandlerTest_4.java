        handler = new RetryWithDelayHandler(MAX_ATTEMPTS, Delay.linear(TimeUnit.MILLISECONDS),
                new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return throwable instanceof OutOfMemoryError;
                    }
                }, null);

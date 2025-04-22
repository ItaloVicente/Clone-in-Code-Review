    public static <T> Observable<T> applyTimeout(final Observable<T> input, final AtomicReference<CouchbaseRequest> request,
        final CouchbaseEnvironment environment, final long timeout, final TimeUnit timeUnit) {
        if (timeout > 0) {
            return input
                .timeout(timeout, timeUnit, environment.scheduler())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable t) {
                        if (t instanceof TimeoutException) {
                            return Observable.error(new TimeoutException(Utils.formatTimeout(
                                request.get(),
                                timeUnit.toMicros(timeout)
                            )));
                        } else {
                            return Observable.error(t);
                        }
                    }
                });
        } else {
            return input;
        }
    }


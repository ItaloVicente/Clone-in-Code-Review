                return Observable.just(r);
            }
        })
        .flatMap(RESULT_PEEK_FOR_RETRY)
        .retryWhen(RetryBuilder
            .anyOf(AnalyticsTemporaryFailureException.class)
            .delay(Delay.exponential(TimeUnit.MILLISECONDS, 500, 2))
            .max(10)
            .doOnRetry(new Action4<Integer, Throwable, Long, TimeUnit>() {
                @Override
                public void call(Integer attempt, Throwable error, Long delay, TimeUnit delayUnit) {
                    LOGGER.debug("Retrying {} because of {} (attempt {}, delay {} {})", query.query(),
                            error.getMessage(), attempt, delay, delayUnit);
                }
            })
            .build()
        )
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends AsyncAnalyticsQueryResult>>() {
            @Override
            public Observable<? extends AsyncAnalyticsQueryResult> call(Throwable throwable) {
                if (throwable instanceof CannotRetryException) {
                    if (throwable.getCause() != null && throwable.getCause() instanceof AnalyticsTemporaryFailureException) {
                        AnalyticsTemporaryFailureException x = (AnalyticsTemporaryFailureException) throwable.getCause();
                        return Observable.just(x.result());
                    }
                }
                return Observable.error(throwable);
            }

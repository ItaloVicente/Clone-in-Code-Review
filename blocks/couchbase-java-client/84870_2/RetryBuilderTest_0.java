
    @Test(expected = DocumentDoesNotExistException.class)
    public void shouldNotIgnoreExceptionOnLastTry() {
        final AtomicInteger attempt = new AtomicInteger(0);
        Observable
            .just(0)
            .flatMap(new Func1<Integer, Observable<Integer>>() {
                @Override
                public Observable<Integer> call(Integer l) {
                    int at = attempt.getAndIncrement();
                    if (at == 0 || at == 1) {
                        return Observable.error(new TimeoutException());
                    } else {
                        return Observable.error(new DocumentDoesNotExistException());
                    }
                }
            })
            .retryWhen(RetryBuilder.anyOf(TimeoutException.class).max(2).build())
            .toBlocking()
            .single();
    }

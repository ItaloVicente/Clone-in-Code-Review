        Func1<Throwable, Observable<AsyncQueryResult>> retryFunction = new Func1<Throwable, Observable<AsyncQueryResult>>() {
            @Override
            public Observable<AsyncQueryResult> call(Throwable throwable) {
                return retryPrepareAndExecuteOnce(throwable, query);
            }
        };

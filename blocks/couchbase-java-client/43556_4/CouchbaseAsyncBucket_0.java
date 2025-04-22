
        Observable<ViewQueryResponse> source = Observable.defer(new Func0<Observable<ViewQueryResponse>>() {
            @Override
            public Observable<ViewQueryResponse> call() {
                return core.send(request);
            }
        });

        return ViewRetryHandler
            .retryOnCondition(source)

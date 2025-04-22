
        Observable<ObserveResponse> observeResponses = sendObserveRequests(core, bucket, id, cas, persistTo,
            replicateTo, retryStrategy);

        return observeResponses
                .map(new Func1<ObserveResponse, ObserveItem>() {
                    @Override
                    public ObserveItem call(ObserveResponse observeResponse) {
                        return new ObserveItem(id, observeResponse, cas, remove, persistIdentifier, replicaIdentifier);
                    }
                })
                .scan(new ObserveItem(),
                        new Func2<ObserveItem, ObserveItem, ObserveItem>() {
                            @Override
                            public ObserveItem call(ObserveItem currentStatus, ObserveItem newStatus) {
                                return currentStatus.add(newStatus);
                            }
                        })
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.zipWith(
                            Observable.range(1, Integer.MAX_VALUE),
                            new Func2<Void, Integer, Integer>() {
                                @Override
                                public Integer call(Void aVoid, Integer attempt) {
                                    return attempt;
                                }
                            }
                        )
                        .flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer attempt) {
                                return Observable.timer(delay.calculate(attempt), delay.unit());
                            }
                        });
                    }
                })
                .skipWhile(new Func1<ObserveItem, Boolean>() {
                    @Override
                    public Boolean call(ObserveItem status) {
                        return !status.check(persistTo, replicateTo);
                    }
                })
                .take(1)
                .map(new Func1<ObserveItem, Boolean>() {
                    @Override
                    public Boolean call(ObserveItem observeResponses) {
                        return true;
                    }
                });

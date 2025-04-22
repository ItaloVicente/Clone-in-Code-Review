    public Observable<Boolean> exists(final String id) {
        return Observable.defer(new Func0<Observable<ObserveResponse>>() {
                @Override
                public Observable<ObserveResponse> call() {
                    return core.send(new ObserveRequest(id, 0, true, (short) 0, bucket));
                }
            })

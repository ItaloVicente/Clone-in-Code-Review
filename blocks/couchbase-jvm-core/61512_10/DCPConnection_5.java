    public Observable<ResponseStatus> removeStream(final short partition) {
        if (!streams.contains(partition)) {
            return Observable.just(ResponseStatus.NOT_EXISTS);
        }
        return Observable.defer(new Func0<Observable<StreamCloseResponse>>() {
            @Override
            public Observable<StreamCloseResponse> call() {
                return core.send(new StreamCloseRequest(partition, bucket, password));
            }
        }).map(new Func1<StreamCloseResponse, ResponseStatus>() {
            @Override
            public ResponseStatus call(StreamCloseResponse response) {
                if (response.status() == ResponseStatus.SUCCESS) {
                    streams.remove(partition);
                }
                return response.status();
            }
        });

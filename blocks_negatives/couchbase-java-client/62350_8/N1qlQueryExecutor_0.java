        return Observable.defer(new Func0<Observable<GenericQueryResponse>>() {
            @Override
            public Observable<GenericQueryResponse> call() {
                return core.send(createN1qlRequest(query, bucket, password));
            }
        }).flatMap(new Func1<GenericQueryResponse, Observable<PreparedPayload>>() {

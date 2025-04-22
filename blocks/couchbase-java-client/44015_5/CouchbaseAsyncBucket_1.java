    @Override
    public Observable<AsyncSpatialViewResult> query(final SpatialViewQuery query) {
        final ViewQueryRequest request = new ViewQueryRequest(query.getDesign(), query.getView(), query.isDevelopment(),
            true, query.toString(), bucket, password);

        Observable<ViewQueryResponse> source = Observable.defer(new Func0<Observable<ViewQueryResponse>>() {
            @Override
            public Observable<ViewQueryResponse> call() {
                return core.send(request);
            }
        });

        return ViewRetryHandler
            .retryOnCondition(source)
            .flatMap(new Func1<ViewQueryResponse, Observable<AsyncSpatialViewResult>>() {
                @Override
                public Observable<AsyncSpatialViewResult> call(final ViewQueryResponse response) {
                    return ViewQueryResponseMapper.mapToSpatialViewResult(CouchbaseAsyncBucket.this, query, response);
                }
            });
    }


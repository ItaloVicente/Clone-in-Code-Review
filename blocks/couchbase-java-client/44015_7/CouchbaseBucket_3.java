                        asyncViewResult.error(), asyncViewResult.debug()
                    );
                }
            })
            .single(), timeout, timeUnit);
    }


    @Override
    public SpatialViewResult query(SpatialViewQuery query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .query(query)
            .map(new Func1<AsyncSpatialViewResult, SpatialViewResult>() {
                @Override
                public SpatialViewResult call(AsyncSpatialViewResult asyncSpatialViewResult) {
                    return new DefaultSpatialViewResult(environment, CouchbaseBucket.this,
                        asyncSpatialViewResult.rows(), asyncSpatialViewResult.success(),
                        asyncSpatialViewResult.error(), asyncSpatialViewResult.debug()
                    );

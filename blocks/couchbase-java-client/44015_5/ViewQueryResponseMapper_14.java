    public static Observable<AsyncSpatialViewResult> mapToSpatialViewResult(final AsyncBucket bucket,
        final SpatialViewQuery query, final ViewQueryResponse response) {

        return response
            .info()
            .map(new ByteBufToJsonObject())
            .map(new BuildSpatialViewResult(bucket, query, response));
    }


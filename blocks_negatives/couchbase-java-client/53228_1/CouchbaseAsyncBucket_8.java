        GenericQueryRequest request = GenericQueryRequest.jsonQuery(query, bucket, password);
        return core
            .<GenericQueryResponse>send(request)
            .flatMap(new Func1<GenericQueryResponse, Observable<AsyncQueryResult>>() {
                @Override
                public Observable<AsyncQueryResult> call(final GenericQueryResponse response) {
                    final Observable<AsyncQueryRow> rows = response.rows().map(new Func1<ByteBuf, AsyncQueryRow>() {

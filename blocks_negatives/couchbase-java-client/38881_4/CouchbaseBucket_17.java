    @Override
    public Observable<QueryResult> query(final Query query) {
        return query(query.toString());
    }

    @Override
    public Observable<QueryResult> query(final String query) {
        final Converter<?, ?> converter = converters.get(JsonDocument.class);
        GenericQueryRequest request = new GenericQueryRequest(query, bucket, password);
        return core
            .<GenericQueryResponse>send(request)
            .filter(new Func1<GenericQueryResponse, Boolean>() {
                @Override
                public Boolean call(GenericQueryResponse response) {
                    return response.content() != null;
                }
            })
            .map(new Func1<GenericQueryResponse, QueryResult>() {
                @Override
                public QueryResult call(GenericQueryResponse response) {
                    ByteBuf content = Unpooled.copiedBuffer(response.content(), CharsetUtil.UTF_8);
                    QueryResult result = new QueryResult((JsonObject) converter.decode(content));
                    content.release();
                    return result;
                }
            });
    }

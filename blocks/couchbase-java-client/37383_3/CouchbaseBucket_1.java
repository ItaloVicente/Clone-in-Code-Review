    @Override
    public Observable<N1qlRow> query(N1qlQuery query) {
        final Converter<?, ?> converter = converters.get(JsonDocument.class);
        GenericQueryRequest request = new GenericQueryRequest(query.export(), bucket, password);
        return core
            .<GenericQueryResponse>send(request)
            .filter(new Func1<GenericQueryResponse, Boolean>() {
                @Override
                public Boolean call(GenericQueryResponse response) {
                    return response.content() != null;
                }
            })
            .map(new Func1<GenericQueryResponse, N1qlRow>() {
                @Override
                public N1qlRow call(GenericQueryResponse response) {
                    return new N1qlRow((JsonObject) converter.decode(Unpooled.copiedBuffer(response.content(), CharsetUtil.UTF_8)));
                }
            });
    }

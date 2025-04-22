  @Override
  public Observable<QueryResult> query(final String query) {
    final Converter<?, ?> converter = converters.get(JsonDocument.class);
    final GenericQueryRequest request = new GenericQueryRequest(query, bucket, password);

    return core
        .<GenericQueryResponse>send(request)
        .filter(new Func1<GenericQueryResponse, Boolean>() {
          @Override
          public Boolean call(final GenericQueryResponse response) {
            return response.content() != null;
          }
        })
        .map(new Func1<GenericQueryResponse, QueryResult>() {
          @Override
          public QueryResult call(final GenericQueryResponse response) {
            final ByteBuf content = Unpooled.copiedBuffer(response.content(), CharsetUtil.UTF_8);
            final QueryResult result = new QueryResult((JsonObject) converter.decode(content, 0));
            content.release();
            return result;
          }
        });
  }

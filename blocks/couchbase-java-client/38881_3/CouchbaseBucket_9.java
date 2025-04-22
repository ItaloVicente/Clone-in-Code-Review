  @Override
  public Observable<QueryResult> query(final Query query) {
    return query(query.toString());
  }

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
            return new QueryResult(((JacksonJsonConverter) converter).decode(response.content()));
          }
        });
  }

  @Override
  public Observable<FlushResponse> flush()
  {
    final FlushRequest request = new FlushRequest(bucket, password);

    return core.send(request);
  }

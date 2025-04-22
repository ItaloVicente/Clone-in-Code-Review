  @Override
  @SuppressWarnings("unchecked")
  public <D extends Document<?>> Observable<D> insert(final D document) {
    final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    ByteBuf content = converter.encode(document.content());
    return core
      .<InsertResponse>send(new InsertRequest(document.id(), content, bucket))
      .map(new Func1<InsertResponse, D>() {
        @Override
        public D call(InsertResponse response) {
          return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(),
              response.status());
        }
      });
  }

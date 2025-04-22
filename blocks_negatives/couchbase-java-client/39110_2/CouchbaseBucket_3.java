  @Override
  @SuppressWarnings("unchecked")
  public <D extends Document<?>> Observable<D> get(final String id, final Class<D> target) {
    return core.<GetResponse>send(new GetRequest(id, bucket)).map(new Func1<GetResponse, D>() {
        @Override
        public D call(final GetResponse response) {
          Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
          Object content = response.status() == ResponseStatus.SUCCESS ? converter.decode(response.content()) : null;
          return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
        }
      }
    );
  }

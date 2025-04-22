    final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    ByteBuf content = converter.encode(document.content());
    return core.<UpsertResponse>send(new UpsertRequest(document.id(), content, bucket))
      .map(new Func1<UpsertResponse, D>() {
        @Override
        public D call(UpsertResponse response) {
            return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(),
                response.status());
        }
      });

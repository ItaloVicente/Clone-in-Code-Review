    final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    ByteBuf content = converter.encode(document.content());
    return core.<ReplaceResponse>send(new ReplaceRequest(document.id(), content, bucket))
      .map(new Func1<ReplaceResponse, D>() {
        @Override
        public D call(ReplaceResponse response) {
            return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(),
                response.status());
        }
      });

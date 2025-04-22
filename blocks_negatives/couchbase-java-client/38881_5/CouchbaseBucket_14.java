      final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    RemoveRequest request = new RemoveRequest(document.id(), document.cas(),
      bucket);
    return core.<RemoveResponse>send(request).map(new Func1<RemoveResponse, D>() {
      @Override
      public D call(RemoveResponse response) {
          return (D) converter.newDocument(document.id(), document.content(), document.cas(), document.expiry(),
              response.status());
      }
    });

    final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    ByteBuf content = converter.encode(document.content());
    return core.<ReplaceResponse>send(new ReplaceRequest(document.id(), content, document.cas(), document.expiry(), 0, bucket))
      .flatMap(new Func1<ReplaceResponse, Observable<D>>() {
          @Override
          public Observable<D> call(ReplaceResponse response) {
              if (response.status() == ResponseStatus.NOT_EXISTS) {
                  return Observable.error(new DocumentDoesNotExistException());
              }
              return Observable.just((D) converter.newDocument(document.id(), document.content(), response.cas(),
                  document.expiry(), response.status()));
          }
      });

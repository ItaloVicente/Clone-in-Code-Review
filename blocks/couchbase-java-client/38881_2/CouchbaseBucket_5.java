    final CachedData cachedData = converter.encode(document.content());

    return core
        .<UpsertResponse>send(new UpsertRequest(document.id(), cachedData.getBuffer(), 0, cachedData.getFlags(), bucket))
        .map(new Func1<UpsertResponse, D>() {
          @Override
          public D call(final UpsertResponse response) {
            return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(), response.status());
          }
        });

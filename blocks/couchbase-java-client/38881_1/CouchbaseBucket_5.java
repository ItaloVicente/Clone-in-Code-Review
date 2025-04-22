    final CachedData cachedData = converter.encode(document.content());

    return core
        .<ReplaceResponse>send(new ReplaceRequest(document.id(), cachedData.getBuffer(), 0, 0, cachedData.getFlags(), bucket))
        .map(new Func1<ReplaceResponse, D>() {
          @Override
          public D call(final ReplaceResponse response) {
            return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(), response.status());
          }
        });

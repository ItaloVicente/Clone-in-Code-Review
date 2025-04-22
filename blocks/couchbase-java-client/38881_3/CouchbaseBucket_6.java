    final CoreDocument coreDocument = converter.encode(document);

    return core
        .<ReplaceResponse>send(new ReplaceRequest(document.id(), coreDocument, bucket))
        .map(new Func1<ReplaceResponse, D>() {
          @Override
          public D call(final ReplaceResponse response) {
            return (D) document.copy(response.cas(), response.status());
          }
        });

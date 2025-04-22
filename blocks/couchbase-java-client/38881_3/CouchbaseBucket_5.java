    final CoreDocument coreDocument = converter.encode(document);

    return core
        .<UpsertResponse>send(new UpsertRequest(document.id(), coreDocument, bucket))
        .map(new Func1<UpsertResponse, D>() {
          @Override
          public D call(final UpsertResponse response) {
            return (D) document.copy(response.cas(), response.status());
          }
        });

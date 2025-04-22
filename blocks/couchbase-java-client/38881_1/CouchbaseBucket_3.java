        .<InsertResponse>send(new InsertRequest(document.id(), cachedData.getBuffer(), 0, cachedData.getFlags(), bucket))
        .map(new Func1<InsertResponse, D>() {
          @Override
          public D call(final InsertResponse response) {
            return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(), response.status());
          }
        });

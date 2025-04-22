        .<InsertResponse>send(new InsertRequest(document.id(), coreDocument, bucket))
        .map(new Func1<InsertResponse, D>() {
          @Override
          public D call(final InsertResponse response) {
            return (D) document.copy(response.cas(), response.status());
          }
        });

      .<InsertResponse>send(new InsertRequest(document.id(), content, bucket))
      .map(new Func1<InsertResponse, D>() {
        @Override
        public D call(InsertResponse response) {
          return (D) converter.newDocument(document.id(), document.content(), response.cas(), document.expiry(),
              response.status());
        }
      });

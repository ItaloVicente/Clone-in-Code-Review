    return core
        .<RemoveResponse>send(new RemoveRequest(document.id(), document.cas(), bucket))
        .map(new Func1<RemoveResponse, D>() {
          @Override
          public D call(final RemoveResponse response) {
            return (D) document.copy(document.cas(), response.status());
          }
        });

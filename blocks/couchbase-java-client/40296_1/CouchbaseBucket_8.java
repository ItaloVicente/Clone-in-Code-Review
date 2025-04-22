          public Observable<D> call(ReplaceResponse response) {
              if (response.status() == ResponseStatus.NOT_EXISTS) {
                  return Observable.error(new DocumentDoesNotExistException());
              }
              return Observable.just((D) converter.newDocument(document.id(), document.content(), response.cas(),
                  document.expiry(), response.status()));

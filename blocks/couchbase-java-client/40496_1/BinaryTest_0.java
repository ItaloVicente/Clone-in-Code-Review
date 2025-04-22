          BlockingObservable<JsonDocument> observable = Observable
              .from("doc1", "doc2", "doc3")
              .flatMap(new Func1<String, Observable<JsonDocument>>() {
                  @Override
                  public Observable<JsonDocument> call(String id) {
                      return bucket().get(id);
                  }
              }).toBlocking();

          Iterator<JsonDocument> iterator = observable.getIterator();
          while (iterator.hasNext()) {
              JsonDocument doc = iterator.next();
              assertNull(doc.content());
              assertEquals(ResponseStatus.NOT_EXISTS, doc.status());
          }

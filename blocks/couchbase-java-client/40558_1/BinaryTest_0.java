    @Test
    public void shouldLoadMultipleDocuments() throws Exception {
        BlockingObservable<JsonDocument> observable = Observable
          .from("doc1", "doc2", "doc3")
          .flatMap(new Func1<String, Observable<JsonDocument>>() {
              @Override
              public Observable<JsonDocument> call(String id) {
                  return bucket().get(id);
              }
          }).toBlocking();


        final AtomicInteger counter = new AtomicInteger();
        observable.forEach(new Action1<JsonDocument>() {
            @Override
            public void call(JsonDocument document) {
                counter.incrementAndGet();
            }
        });
        assertEquals(0, counter.get());
    }

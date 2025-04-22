  <D extends Document<?>> Observable<D> upsert(D document);

  <D extends Document<?>> Observable<D> replace(D document);


  <D extends Document<?>> Observable<D> remove(D document);

  Observable<JsonDocument> remove(String id);

  <D extends Document<?>> Observable<D> remove(String id, Class<D> target);

  Observable<ViewRow> query(ViewQuery query);

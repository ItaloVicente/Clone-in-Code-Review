  /**
   * Insert a {@link Document}.
   *
   * @param document the document to insert.
   * @param <D> the type of the document, which is inferred from the instance.
   * @return the document again.
   */
  <D extends Document<?>> Observable<D> insert(D document);

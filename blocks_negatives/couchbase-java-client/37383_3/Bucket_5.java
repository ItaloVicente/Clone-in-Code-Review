  /**
   * Remove the given {@link Document}.
   *
   * @param document
   * @param <D>
   * @return
   */
  <D extends Document<?>> Observable<D> remove(D document);

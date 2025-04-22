  /**
   * Remove the document by the given ID and cast it to a custom target document.
   *
   * @param id
   * @param target
   * @param <D>
   * @return
   */
  <D extends Document<?>> Observable<D> remove(String id, Class<D> target);

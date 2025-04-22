  /**
   * Get a {@link Document} by its unique ID.
   *
   * The loaded document will be converted into the target class, which needs
   * a custom converter registered with the system.
   *
   * @param id the ID of the document.
   * @param target the document type.
   * @return the loaded and converted document.
   */
  <D extends Document<?>> Observable<D> get(String id, Class<D> target);

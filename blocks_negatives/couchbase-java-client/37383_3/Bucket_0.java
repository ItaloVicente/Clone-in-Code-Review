  /**
   * Get a {@link Document} by its unique ID.
   *
   * The loaded document will be converted using the default converter, which is
   * JSON if not configured otherwise.
   *
   * @param id the ID of the document.
   * @return the loaded and converted document.
   */
  Observable<JsonDocument> get(String id);

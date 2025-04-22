  /**
   * Queries a View defined by the {@link ViewQuery} and returns a {@link ViewRow}
   * for each emitted row in the view.
   *
   * @param query the query for the view.
   * @return a row for each result (from 0 to N).
   */
  Observable<ViewRow> query(ViewQuery query);

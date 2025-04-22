  /**
   * A paginated query allows the user to get the results of a large query in
   * small chunks allowing for better performance. The result allows you
   * to iterate through the results of the query and when you get to the end
   * of the current result set the client will automatically fetch the next set
   * of results.
   *
   * @param view the view to query against.
   * @param query the query for this request.
   * @param docsPerPage the amount of documents per page.
   * @return A Paginator (iterator) to use for reading the results of the query.
   */

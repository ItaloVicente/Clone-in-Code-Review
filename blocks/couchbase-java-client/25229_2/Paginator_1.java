  public Paginator(final CouchbaseClient client, final View view,
    final Query query, final int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Number of documents per page "
        + "must be greater than zero.");
    }


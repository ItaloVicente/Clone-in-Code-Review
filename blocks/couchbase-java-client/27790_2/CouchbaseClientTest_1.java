  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfBucketIsNull() throws Exception {
    new CouchbaseClient(uris, null, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfBucketIsEmpty() throws Exception {
    new CouchbaseClient(uris, "", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfPasswordIsNull() throws Exception {
    new CouchbaseClient(uris, "default", null);
  }


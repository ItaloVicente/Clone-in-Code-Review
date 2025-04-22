  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfBucketIsNull() throws Exception {
    new CouchbaseConnectionFactory(uris, null, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfBucketIsEmpty() throws Exception {
    new CouchbaseConnectionFactory(uris, "", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfPasswordIsNull() throws Exception {
    new CouchbaseConnectionFactory(uris, "default", null);
  }


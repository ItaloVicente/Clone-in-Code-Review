  @Test
  public void testDefaultAuthDescriptor() throws Exception {
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();

    CouchbaseConnectionFactory factory =
      instance.buildCouchbaseConnection(uris, "foo", "bar");

    AuthDescriptor descriptor = factory.getAuthDescriptor();
    assertEquals(0, descriptor.getMechs().length);
  }

  @Test
  public void testOverridingAuthDescriptor() throws Exception {
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();

    instance.setAuthDescriptor(new AuthDescriptor(
      new String[] {"PLAIN", "CRAM-MD5"},
      new PlainCallbackHandler("foo", "bar")
    ));

    CouchbaseConnectionFactory factory =
      instance.buildCouchbaseConnection(uris, "foo", "bar");

    AuthDescriptor descriptor = factory.getAuthDescriptor();
    assertEquals(2, descriptor.getMechs().length);
  }


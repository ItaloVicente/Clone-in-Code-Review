
  public void testBuildAuthHeader() throws UnsupportedEncodingException {
    ConfigurationProviderHTTP.buildAuthHeader("foo", "bar");
  }

  public void testBuildAuthHeaderUTF8() throws UnsupportedEncodingException {
    String result = ConfigurationProviderHTTP.buildAuthHeader("blahblah",
        "bla@@h");
    assertEquals("Basic YmxhaGJsYWg6YmxhQEBo", result);
  }

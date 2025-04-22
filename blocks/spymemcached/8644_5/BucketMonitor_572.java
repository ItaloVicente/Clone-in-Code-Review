    assert (channel != null);
  }

  protected HttpRequest prepareRequest(URI uri, String h) {
    HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
      HttpMethod.GET, uri.toASCIIString());
    request.setHeader(HttpHeaders.Names.HOST, h);
    if (getHttpUser() != null) {
      String basicAuthHeader;
      try {
        basicAuthHeader =
            ConfigurationProviderHTTP.buildAuthHeader(getHttpUser(),
            getHttpPass());
        request.setHeader(HttpHeaders.Names.AUTHORIZATION, basicAuthHeader);
      } catch (UnsupportedEncodingException ex) {
        throw new RuntimeException("Could not encode specified credentials"
            + " for HTTP request.", ex);
      }

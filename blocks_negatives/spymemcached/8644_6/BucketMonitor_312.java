
    protected HttpRequest prepareRequest(URI uri, String h) {
        HttpRequest request = new DefaultHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
        request.setHeader(HttpHeaders.Names.HOST, h);
        if (getHttpUser() != null) {
	    String basicAuthHeader;
	    try {
		basicAuthHeader = ConfigurationProviderHTTP.buildAuthHeader(getHttpUser(), getHttpPass());
		request.setHeader(HttpHeaders.Names.AUTHORIZATION, basicAuthHeader);
	    } catch (UnsupportedEncodingException ex) {
		throw new RuntimeException("Could not encode specified credentials for HTTP request.", ex);
	    }
        }
        request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);  // No keep-alives for this
        request.setHeader(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_CACHE);
        request.setHeader(HttpHeaders.Names.ACCEPT, "application/json");
        request.setHeader(HttpHeaders.Names.USER_AGENT, "spymemcached vbucket client");
        request.setHeader("X-memcachekv-Store-Client-Specification-Version", CLIENT_SPEC_VER);
        return request;

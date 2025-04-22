	    String basicAuthHeader;
	    try {
		basicAuthHeader = ConfigurationProviderHTTP.buildAuthHeader(getHttpUser(), getHttpPass());
		request.setHeader(HttpHeaders.Names.AUTHORIZATION, basicAuthHeader);
	    } catch (UnsupportedEncodingException ex) {
		throw new RuntimeException("Could not encode specified credentials for HTTP request.", ex);
	    }

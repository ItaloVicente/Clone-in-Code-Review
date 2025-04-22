    /**
     * Create a URL which has the appropriate headers to interact with the
     * service.  Most exception handling is up to the caller.
     *
     * @param resource the URI either absolute or relative to the base for this ClientManager
     * @return
     * @throws java.io.IOException
     */
    private URLConnection urlConnBuilder(URI base, URI resource) throws IOException {
        if (!resource.isAbsolute() && base != null) {
            resource = base.resolve(resource);
        }
        URL specURL = resource.toURL();
        URLConnection connection = specURL.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("user-agent", "spymemcached vbucket client");
        connection.setRequestProperty("X-memcachekv-Store-Client-Specification-Version", CLIENT_SPEC_VER);
	if (restUsr != null) {
	    try {
		connection.setRequestProperty("Authorization", buildAuthHeader(restUsr, restPwd));
	    } catch (UnsupportedEncodingException ex) {
		throw new IOException("Could not encode specified credentials for HTTP request.", ex);
	    }
	}

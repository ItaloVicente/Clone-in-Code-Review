	/**
	 * Open an HTTP connection, setting the accept-encoding request header to gzip.
	 *
	 * @param method HTTP request method
	 * @param u url of the HTTP connection
	 * @return the HTTP connection
	 * @throws java.io.IOException
	 * @since 3.3
	 * @deprecated use {@link #httpOpen(String, URL, AcceptEncoding)} instead.
	 */
	@Deprecated
	protected HttpConnection httpOpen(String method, URL u) throws IOException {
		return httpOpen(method, u, AcceptEncoding.GZIP);
	}


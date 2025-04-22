	/**
	 * State required to speak multiple HTTP requests with the remote.
	 * <p>
	 * A service wrapper provides a normal looking InputStream and OutputStream
	 * pair which are connected via HTTP to the named remote service. Writing to
	 * the OutputStream is buffered until either the buffer overflows, or
	 * reading from the InputStream occurs. If overflow occurs HTTP/1.1 and its
	 * chunked transfer encoding is used to stream the request data to the
	 * remote service. If the entire request fits in the memory buffer, the
	 * older HTTP/1.0 standard and a fixed content length is used instead.
	 * <p>
	 * It is an error to attempt to read without there being outstanding data
	 * ready for transmission on the OutputStream.
	 * <p>
	 * No state is preserved between write-read request pairs. The caller is
	 * responsible for replaying state vector information as part of the request
	 * data written to the OutputStream. Any session HTTP cookies may or may not
	 * be preserved between requests, it is left up to the JVM's implementation
	 * of the HTTP client.
	 */
	class Service {
		private final String serviceName;

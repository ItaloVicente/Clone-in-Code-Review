	 * @param pack the cached pack for which to check if a corresponding URI
	 *	exists
	 * @param protocolsSupported the protocols that the client has declared
	 *	support for; if a URI is returned, it must be of one of these
	 *	protocols
	 * @throws IOException implementations may throw this
	 * @return if a URI corresponds to the cached pack, an object
	 *	containing the URI and some other information; null otherwise
	 * @since 5.5

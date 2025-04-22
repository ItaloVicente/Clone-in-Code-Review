	/**
	 * Creates a {@link URIish} from a given string. The
	 * {@link CredentialsProvider} uses uris as resource identifications.
	 *
	 * @param resourceKey
	 *            to convert
	 * @return the uri
	 */
	protected URIish toUri(String resourceKey) {
		try {
			return new URIish(resourceKey);
		} catch (URISyntaxException e) {
		}
	}

	private char[] getPassword(String resourceKey, String message) {

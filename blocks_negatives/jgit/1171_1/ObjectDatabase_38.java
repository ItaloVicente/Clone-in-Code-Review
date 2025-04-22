	 *             the alternate list could not be accessed. The empty alternate
	 *             array {@link #NO_ALTERNATES} will be assumed by the caller.
	 */
	protected ObjectDatabase[] loadAlternates() throws IOException {
		return NO_ALTERNATES;
	}

	/**
	 * Close the list of alternates returned by {@link #loadAlternates()}.
	 *
	 * @param alt
	 *            the alternate list, from {@link #loadAlternates()}.
	 */
	protected void closeAlternates(ObjectDatabase[] alt) {
		for (final ObjectDatabase d : alt) {
			d.close();

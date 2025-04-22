	public MemcachedClient(final List<URI> baseList,
		final String bucketName,
		final String usr, final String pwd) throws IOException, ConfigurationException {
		for (URI bu : baseList) {
			if (!bu.isAbsolute()) {
				throw new IllegalArgumentException("The base URI must be absolute");
			}
		}

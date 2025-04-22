	 * @since 5.1
	 */
	protected void addDeletion(String path, boolean isFile,
			Attributes attributes) throws IOException {
		toBeDeleted.add(path);
		if (isFile) {
			addCheckoutMetadata(cleanupMetadata, path, attributes);
		}
	}

	/**

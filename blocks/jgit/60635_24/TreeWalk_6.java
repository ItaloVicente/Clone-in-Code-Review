	private TreeWalk(final @Nullable Repository repo
			final boolean closeReader) {
		if (repo != null) {
			config = repo.getConfig();
			attributesNodeProvider = repo.createAttributesNodeProvider();
		} else {
			config = null;
			attributesNodeProvider = null;
		}

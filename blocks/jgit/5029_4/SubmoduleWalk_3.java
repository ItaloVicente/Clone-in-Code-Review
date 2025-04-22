	public static Repository getSubmoduleRepository(final File parent
			final String path) throws IOException {
		File subWorkTree = new File(parent
		if (!subWorkTree.isDirectory())
			return null;
		File workTree = new File(parent
		try {
					.build();
		} catch (RepositoryNotFoundException e) {
			return null;
		}

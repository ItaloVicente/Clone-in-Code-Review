	private static void assertNormalized(final String name
			final String expected) {
		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			String normalized = Repository.normalizeBranchName(name);
			assertEquals("Normalization of " + name
			assertEquals("\"" + normalized + "\""
					Repository.isValidRefName(Constants.R_HEADS + normalized));
			setWindowsSystemReader();
			normalized = Repository.normalizeBranchName(name);
			assertEquals("Normalization of " + name
			assertEquals("\"" + normalized + "\""
					Repository.isValidRefName(Constants.R_HEADS + normalized));
		} finally {
			SystemReader.setInstance(instance);
		}
	}


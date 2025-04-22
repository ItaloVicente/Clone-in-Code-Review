	protected void resetRepositoryToCreateInitialTag() throws Exception {
		Thread.sleep(2000);
		ResetOperation rop = new ResetOperation(
				lookupRepository(repositoryFile), Constants.R_TAGS +
						INITIAL_TAG, ResetType.HARD);
		rop.execute(new NullProgressMonitor());
		Thread.sleep(2000);
		rop = new ResetOperation(
				lookupRepository(repositoryFile), Constants.R_TAGS +
						INITIAL_TAG, ResetType.HARD);
		rop.execute(new NullProgressMonitor());
	}

	public static void createTag(String tagName)

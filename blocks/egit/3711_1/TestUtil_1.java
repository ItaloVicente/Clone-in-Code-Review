	
	public static void checkHeadCommit(Repository repository, String author,
			String committer, String message) throws Exception {
		CommitInfo commitInfo = CommitHelper.getHeadCommitInfo(repository);
		assertEquals(author, commitInfo.getAuthor());
		assertEquals(committer, commitInfo.getCommitter());
		assertEquals(message, commitInfo.getCommitMessage());
	}

	public static void configureTestCommitterAsUser(Repository repository) {
		StoredConfig config = repository.getConfig();
		config.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, TestUtil.TESTCOMMITTER_NAME);
		config.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_EMAIL, TestUtil.TESTCOMMITTER_EMAIL);
	}


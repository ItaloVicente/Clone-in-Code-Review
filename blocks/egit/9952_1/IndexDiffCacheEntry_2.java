	private String getUpdateJobName() {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		return MessageFormat.format(
				CoreText.IndexDiffCacheEntry_reindexingincrementally, repoName);
	}


		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repo);
		return handleUncommittedFiles(repo, shell,
				MessageFormat.format(
						UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
						repoName));
	}

	public static boolean handleUncommittedFiles(Repository repo, Shell shell,
			String dialogTitle) throws GitAPIException {

		final UserConfig config = repo.getConfig().get(UserConfig.KEY);
		author = config.getAuthorName();
		final String authorEmail = config.getAuthorEmail();
		author = author + " <" + authorEmail + ">"; //$NON-NLS-1$ //$NON-NLS-2$

		committer = config.getCommitterName();
		final String committerEmail = config.getCommitterEmail();
		committer = committer + " <" + committerEmail + ">"; //$NON-NLS-1$ //$NON-NLS-2$

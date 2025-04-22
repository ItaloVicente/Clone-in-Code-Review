		final RevisionInformation info = new RevisionInformation();

		final BlameCommand command = new BlameCommand(repository)
				.setFollowFileRenames(true).setFilePath(path);
		if (startCommit != null)
			command.setStartCommit(startCommit);
		else {


		final BlameCommand command = new BlameCommand(repository)
				.setFollowFileRenames(true).setFilePath(
						mapping.getRepoRelativePath(file));
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.BLAME_IGNORE_WHITESPACE))
			command.setTextComparator(RawTextComparator.WS_IGNORE_ALL);

		final BlameResult result = command.call();

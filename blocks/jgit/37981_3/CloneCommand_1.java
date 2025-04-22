		if (gitDir != null && gitDir.exists() && gitDir.listFiles().length != 0)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().cloneNonEmptyDirectory
		if (directory != null)
			command.setDirectory(directory);
		if (gitDir != null)
			command.setGitDir(gitDir);

		try {
			FileKey key = FileKey.lenient(dstGitdir
		} catch (RepositoryNotFoundException notFound) {
			throw die(MessageFormat.format(CLIText.get().notAGitRepository
					dstGitdir.getPath()));
		}


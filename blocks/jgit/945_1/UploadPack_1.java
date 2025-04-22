		try {
			FileKey key = FileKey.lenient(srcGitdir
		} catch (RepositoryNotFoundException notFound) {
			throw die(MessageFormat.format(CLIText.get().notAGitRepository
					srcGitdir.getPath()));
		}

		up = new org.eclipse.jgit.transport.UploadPack(db);

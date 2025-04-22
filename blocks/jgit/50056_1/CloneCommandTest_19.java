		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertFalse(
					db2.getConfig()
							.getBoolean("branch"
		}

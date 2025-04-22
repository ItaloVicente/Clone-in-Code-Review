		try (
				Git git2 = Git.cloneRepository()
						.setBare(true)
						.setDirectory(directory)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
					fetchRefSpec(db2));
		}

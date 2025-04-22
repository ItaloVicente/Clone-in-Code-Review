		try (
				Git git2 = Git.cloneRepository()
						.setBare(true)
						.setDirectory(directory)
						.setRemote(null)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
					db2.getConfig().getStringList(
							"remote"
			assertEquals("origin"
					db2.getConfig().getString("branch"
		}

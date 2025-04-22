		try (
				Git git2 = Git.cloneRepository()
						.setBare(true)
						.setDirectory(directory)
						.setRemote("upstream")
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
					db2.getConfig().getStringList(
							"remote"
			assertEquals("upstream"
					db2.getConfig().getString("branch"
			assertNull(git2.getRepository().resolve("upstream/test"));
		}

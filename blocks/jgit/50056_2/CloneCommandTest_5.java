		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setRemote("upstream")
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
					db2.getConfig().getStringList(
							"remote"
			assertEquals("upstream"
					db2.getConfig().getString("branch"
			assertEquals(db.resolve("test")
					git2.getRepository().resolve("upstream/test"));
		}

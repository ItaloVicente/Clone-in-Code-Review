		try (
				Git git2 = Git.cloneRepository()
						.setBranch("test")
						.setDirectory(directory)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals("refs/heads/test"
		}

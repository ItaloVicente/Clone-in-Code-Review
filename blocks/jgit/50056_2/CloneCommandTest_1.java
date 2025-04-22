		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setGitDir(new File(directory
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals(directory
			assertEquals(new File(directory
		}

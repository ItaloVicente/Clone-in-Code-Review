		try (
				Git git2 = Git.cloneRepository()
						.setBare(true)
						.setGitDir(gDir)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			try {
				db2.getWorkTree();
				fail("Expected NoWorkTreeException");
			} catch (NoWorkTreeException e) {
				assertEquals(gDir
			}

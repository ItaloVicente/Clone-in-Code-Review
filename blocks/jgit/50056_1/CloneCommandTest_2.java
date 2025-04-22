		try (
				Git git2 = Git.cloneRepository()
						.setDirectory(directory)
						.setGitDir(gDir)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals(directory
			assertEquals(gDir
			assertTrue(new File(directory
			assertFalse(new File(gDir
		}

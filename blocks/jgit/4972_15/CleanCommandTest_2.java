		assertEquals(4
		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
	}

	@Test
	public void testCleanDirsWithDryRun() throws NoWorkTreeException
			GitAPIException {
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setDryRun(true)
				.setCleanDirectories(true).call();

		status = git.status().call();
		files = status.getUntracked();

		assertTrue(files.size() == 4);

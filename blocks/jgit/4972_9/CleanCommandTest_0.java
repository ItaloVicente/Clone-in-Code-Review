		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(!cleanedFiles.contains("sub-clean/File4.txt"));
	}

	@Test
	public void testCleanDirs() throws NoWorkTreeException
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		status = git.status().call();
		files = status.getUntracked();


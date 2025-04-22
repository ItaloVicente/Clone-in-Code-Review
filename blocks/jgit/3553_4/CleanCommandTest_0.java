	@Test
	public void testCleanWithDryRun() throws NoWorkTreeException
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setDryRun(true).call();

		status = git.status().call();
		files = status.getUntracked();

		assertTrue(files.size() == 2);
		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
	}


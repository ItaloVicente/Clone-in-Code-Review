		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
	}

	@Test
	public void testCleanWithDryRunAndNoIgnore() throws NoWorkTreeException
			IOException {
		Set<String> cleanedFiles = git.clean().setDryRun(true).setIgnore(false)
				.call();

		Status status = git.status().call();
		Set<String> files = status.getIgnoredNotInIndex();

		assertTrue(files.size() == 2);
		assertTrue(cleanedFiles.contains("sub-noclean/Ignored.txt"));
		assertTrue(!cleanedFiles.contains("ignored-dir/"));
	}

	@Test
	public void testCleanDirsWithDryRunAndNoIgnore()
			throws NoWorkTreeException
			IOException {
		Set<String> cleanedFiles = git.clean().setDryRun(true).setIgnore(false)
				.setCleanDirectories(true).call();

		Status status = git.status().call();
		Set<String> files = status.getIgnoredNotInIndex();

		assertTrue(files.size() == 2);
		assertTrue(cleanedFiles.contains("sub-noclean/Ignored.txt"));
		assertTrue(cleanedFiles.contains("ignored-dir/"));

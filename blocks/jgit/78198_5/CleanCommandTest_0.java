
	@Test
	public void testCleanDirsWithRepository() throws Exception {
		String innerRepoName = "inner-repo";
		File innerDir = new File(trash
		innerDir.mkdir();
		InitCommand initRepoCommand = new InitCommand();
		initRepoCommand.setDirectory(innerDir);
		initRepoCommand.call();

		Status beforeCleanStatus = git.status().call();
		Set<String> untrackedFolders = beforeCleanStatus.getUntrackedFolders();
		Set<String> untrackedFiles = beforeCleanStatus.getUntracked();

		assertTrue(untrackedFiles.contains(innerRepoName));

		assertTrue(!untrackedFolders.contains(innerRepoName));

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		assertTrue(!cleanedFiles.contains(innerRepoName + "/"));

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
		assertTrue(cleanedFiles.size() == 4);

		Set<String> forceCleanedFiles = git.clean().setCleanDirectories(true)
				.setForce(true).call();

		assertTrue(forceCleanedFiles.contains(innerRepoName + "/"));
	}

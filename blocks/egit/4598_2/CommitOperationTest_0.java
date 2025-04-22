				Arrays.asList(filesToCommit), EMPTY_FILE_LIST,
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);
		testUtils.assertRepositoryContainsFiles(repository, getRepoRelativePaths(filesToCommit));
	}

	@Test
	public void testCommitUntrackTracked() throws Exception {
		IFile fileA = testUtils.addFileToProject(project.getProject(),
				"foo/a.txt", "some text");
		IFile fileB = testUtils.addFileToProject(project.getProject(),
				"foo/b.txt", "some text");
		testUtils.addFileToProject(project.getProject(), "foo/c.txt",
				"some text");
		IFile[] filesToCommit = { fileA, fileB };
		CommitOperation commitOperation = new CommitOperation(filesToCommit,
				Arrays.asList(filesToCommit), EMPTY_FILE_LIST,
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");

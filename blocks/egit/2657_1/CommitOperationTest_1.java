
	@Test
	public void testCommitUntracked() throws Exception {
		IFile fileA = testUtils.addFileToProject(project.getProject(),
				"foo/a.txt", "some text");
		IFile fileB = testUtils.addFileToProject(project.getProject(),
				"foo/b.txt", "some text");
		testUtils.addFileToProject(project.getProject(), "foo/c.txt",
				"some text");
		IFile[] filesToCommit = { fileA, fileB };
		CommitOperation commitOperation = new CommitOperation(filesToCommit,
				EMPTY_FILE_LIST, Arrays.asList(filesToCommit),
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);
		testUtils.assertRepositoryContainsFiles(repository, getRepoRelativePaths(filesToCommit));
	}

	private String[] getRepoRelativePaths(IFile[] files) {
		ArrayList<String> result = new ArrayList<String>();
		for (IFile file:files)
			result.add(file.getProjectRelativePath().toString());
		return result.toArray(new String[result.size()]);
	}

	@Test
	public void testCommitStaged() throws Exception {
		IFile fileA = testUtils.addFileToProject(project.getProject(),
				"foo/a.txt", "some text");
		IFile fileB = testUtils.addFileToProject(project.getProject(),
				"foo/b.txt", "some text");
		IFile[] filesToCommit = { fileA, fileB };
		CommitOperation commitOperation = new CommitOperation(filesToCommit,
				EMPTY_FILE_LIST, Arrays.asList(filesToCommit),
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);

		testUtils.changeContentOfFile(project.getProject(), fileA,
				"new content of A");
		testUtils.changeContentOfFile(project.getProject(), fileB,
				"new content of B");
		resources.add(fileA);
		resources.add(fileB);
		new AddToIndexOperation(resources).execute(null);
		commitOperation = new CommitOperation(filesToCommit, EMPTY_FILE_LIST,
				EMPTY_FILE_LIST, TestUtils.AUTHOR, TestUtils.COMMITTER,
				"second commit");
		commitOperation.execute(null);

		testUtils.assertRepositoryContainsFilesWithContent(repository,
				"foo/a.txt", "new content of A", "foo/b.txt",
				"new content of B");
	}

	@Test
	public void testCommitIndexSubset() throws Exception {
		IFile fileA = testUtils.addFileToProject(project.getProject(),
				"foo/a.txt", "some text");
		IFile fileB = testUtils.addFileToProject(project.getProject(),
				"foo/b.txt", "some text");
		IFile[] filesToCommit = { fileA, fileB };
		CommitOperation commitOperation = new CommitOperation(filesToCommit,
				EMPTY_FILE_LIST, Arrays.asList(filesToCommit),
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);

		testUtils.changeContentOfFile(project.getProject(), fileA,
				"new content of A");
		testUtils.changeContentOfFile(project.getProject(), fileB,
				"new content of B");
		resources.add(fileA);
		resources.add(fileB);
		new AddToIndexOperation(resources).execute(null);
		IFile[] filesToCommit2 = { fileA };
		commitOperation = new CommitOperation(filesToCommit2, EMPTY_FILE_LIST,
				EMPTY_FILE_LIST, TestUtils.AUTHOR, TestUtils.COMMITTER,
				"second commit");
		commitOperation.execute(null);

		testUtils.assertRepositoryContainsFilesWithContent(repository,
				"foo/a.txt", "new content of A", "foo/b.txt", "some text");
	}

	@Test
	public void testCommitWithStaging() throws Exception {
		IFile fileA = testUtils.addFileToProject(project.getProject(),
				"foo/a.txt", "some text");
		IFile fileB = testUtils.addFileToProject(project.getProject(),
				"foo/b.txt", "some text");
		IFile[] filesToCommit = { fileA, fileB };
		CommitOperation commitOperation = new CommitOperation(filesToCommit,
				EMPTY_FILE_LIST, Arrays.asList(filesToCommit),
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);

		testUtils.changeContentOfFile(project.getProject(), fileA,
				"new content of A");
		testUtils.changeContentOfFile(project.getProject(), fileB,
				"new content of B");
		resources.add(fileA);
		resources.add(fileB);
		commitOperation = new CommitOperation(filesToCommit,
				Arrays.asList(filesToCommit), EMPTY_FILE_LIST,
				TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.execute(null);

		testUtils.assertRepositoryContainsFilesWithContent(repository,
				"foo/a.txt", "new content of A", "foo/b.txt",
				"new content of B");
	}


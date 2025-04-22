		UntrackOperation untrackOperation = new UntrackOperation(Arrays.asList(filesToCommit));
		untrackOperation.execute(new NullProgressMonitor());
		commitOperation = new CommitOperation(filesToCommit,
				null, Arrays.asList(filesToCommit),
				TestUtils.AUTHOR, TestUtils.COMMITTER, "second commit");
		commitOperation.execute(null);
		testUtils.assertRepositoryContainsFiles(repository, getRepoRelativePaths(new IFile[0]));

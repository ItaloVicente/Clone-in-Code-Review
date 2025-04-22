		Status status = new Git(repo).status().call();
		assertFalse(status.hasUncommittedChanges());
		assertFalse(status.getUntracked().isEmpty());
		IProject secondProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);
		IFile commitMe = secondProject.getFile(".project");
		CommitOperation op = new CommitOperation(new IFile[] { commitMe, },
				Collections.singleton(commitMe), TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, "Initial commit");
		op.setAmending(true);
		op.execute(new NullProgressMonitor());

		status = new Git(repo).status().call();
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getUntracked().isEmpty());


	@Test
	public void testCommitAddedToIndexDeletedInWorkspace() throws Exception {
		testUtils.addFileToProject(project.getProject(), "foo/a.txt", "some text");
		resources.add(project.getProject().getFolder("foo"));
		new AddToIndexOperation(resources).execute(null);
		CommitOperation commitOperation = new CommitOperation(null, null, null, TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.setCommitAll(true);
		commitOperation.setRepos(new Repository[] {repository});
		commitOperation.execute(null);

		testUtils.addFileToProject(project.getProject(), "zar/b.txt", "some text");
		resources.add(project.getProject().getFolder("zar"));
		new AddToIndexOperation(resources).execute(null);
		project.getProject().getFile("zar/b.txt").delete(true, null);
		assert !project.getProject().getFile("bar/b.txt").exists();

		IFile[] filesToCommit = new IFile[] { project.getProject().getFile("zar/b.txt") };
		commitOperation = new CommitOperation(filesToCommit, Arrays.asList(filesToCommit), null, TestUtils.AUTHOR, TestUtils.COMMITTER, "first commit");
		commitOperation.setRepos(new Repository[] {repository});
		commitOperation.execute(null);

		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(repository.resolve("HEAD^{tree}"));
		assertTrue(treeWalk.next());
		assertEquals("foo", treeWalk.getPathString());
		treeWalk.enterSubtree();
		assertTrue(treeWalk.next());
		assertEquals("foo/a.txt", treeWalk.getPathString());
		assertFalse(treeWalk.next());
	}


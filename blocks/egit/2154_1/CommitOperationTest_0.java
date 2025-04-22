	@Test
	public void testCommitEmptiedTree() throws Exception {
		testUtils.addFileToProject(project.getProject(),
				"sub1/a.txt", "some text");
		testUtils.addFileToProject(project.getProject(),
				"sub2/b.txt", "some text");
		resources.add(project.getProject().getFolder("sub1"));
		resources.add(project.getProject().getFolder("sub2"));
		new AddToIndexOperation(resources).execute(null);
		CommitOperation commitOperation = new CommitOperation(null, null, null,
				TestUtils.AUTHOR, TestUtils.COMMITTER,
				"first commit");
		commitOperation.setCommitAll(true);
		commitOperation.setRepos(new Repository[]{repository});
		commitOperation.execute(null);

		Git git = new Git(repository);
		Iterator<RevCommit> commits = git.log().call().iterator();
		RevCommit secondCommit = commits.next();
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(secondCommit.getTree().getId());
		treeWalk.setRecursive(true);
		treeWalk.setPostOrderTraversal(true);
		assertTrue(treeWalk.next());
		assertEquals("sub1/a.txt", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub1", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub2/b.txt", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub2", treeWalk.getPathString());
		assertFalse(treeWalk.next());

		project.getProject().getFolder("sub2").delete(IResource.FORCE, null);
		IFile[] filesToCommit = { project.getProject().getFile("sub2/b.txt") };
		ArrayList<IFile> notIndexed = new ArrayList<IFile>();
		notIndexed.add(filesToCommit[0]);
		ArrayList<IFile> notTracked = new ArrayList<IFile>();
		Thread.sleep(1100); // Trouble in "fresh" detection of something
		commitOperation = new CommitOperation(filesToCommit, notIndexed, notTracked, TestUtils.AUTHOR, TestUtils.COMMITTER, "second commit");
		commitOperation.setCommitAll(false);
		commitOperation.execute(null);

		Thread.sleep(1100); // Trouble in "fresh" detection of something
		git = new Git(repository);
		commits = git.log().call().iterator();
		secondCommit = commits.next();
		treeWalk = new TreeWalk(repository);
		treeWalk.addTree(secondCommit.getTree().getId());
		treeWalk.setRecursive(true);
		treeWalk.setPostOrderTraversal(true);
		assertTrue(treeWalk.next());
		assertEquals("sub1/a.txt", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub1", treeWalk.getPathString());
		assertFalse(treeWalk.next());
	}

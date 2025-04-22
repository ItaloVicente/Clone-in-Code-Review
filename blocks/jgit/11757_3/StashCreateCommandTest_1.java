	@Test
	public void newFileInIndexThenModifiedInWorkTree() throws Exception {
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		writeTrashFile("file"
		RevCommit stashedWorkTree = Git.wrap(db).stashCreate().call();
		validateStashedCommit(stashedWorkTree);
		RevWalk walk = new RevWalk(db);
		RevCommit stashedIndex = stashedWorkTree.getParent(1);
		walk.parseBody(stashedIndex);
		walk.parseBody(stashedIndex.getTree());
		walk.parseBody(stashedIndex.getParent(0));
		List<DiffEntry> workTreeStashAgainstWorkTree = diffWorkingAgainstHead(stashedWorkTree);
		assertEquals(1
		List<DiffEntry> workIndexAgainstWorkTree = diffIndexAgainstHead(stashedWorkTree);
		assertEquals(1
		List<DiffEntry> indexStashAgainstWorkTree = diffIndexAgainstWorking(stashedWorkTree);
		assertEquals(1
	}


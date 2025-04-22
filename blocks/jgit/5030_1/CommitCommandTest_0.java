
	@Test
	public void commitNewSubmodule() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path
		assertEquals(commit
		assertEquals(uri
		assertEquals(path
		assertEquals(uri
		assertNotNull(generator.getRepository());
		assertEquals(commit

		RevCommit submoduleCommit = git.commit().setMessage("submodule add")
				.setOnly(path).call();
		assertNotNull(submoduleCommit);
		TreeWalk walk = new TreeWalk(db);
		walk.addTree(commit.getTree());
		walk.addTree(submoduleCommit.getTree());
		walk.setFilter(TreeFilter.ANY_DIFF);
		List<DiffEntry> diffs = DiffEntry.scan(walk);
		assertEquals(1
		DiffEntry subDiff = diffs.get(0);
		assertEquals(FileMode.MISSING
		assertEquals(FileMode.GITLINK
		assertEquals(ObjectId.zeroId()
		assertEquals(commit
		assertEquals(path
	}

	@Test
	public void commitSubmoduleUpdate() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("edit file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path
		assertEquals(commit2
		assertEquals(uri
		assertEquals(path
		assertEquals(uri
		assertNotNull(generator.getRepository());
		assertEquals(commit2

		RevCommit submoduleAddCommit = git.commit().setMessage("submodule add")
				.setOnly(path).call();
		assertNotNull(submoduleAddCommit);

		RefUpdate update = repo.updateRef(Constants.HEAD);
		update.setNewObjectId(commit);
		assertEquals(Result.FORCED

		RevCommit submoduleEditCommit = git.commit()
				.setMessage("submodule add").setOnly(path).call();
		assertNotNull(submoduleEditCommit);
		TreeWalk walk = new TreeWalk(db);
		walk.addTree(submoduleAddCommit.getTree());
		walk.addTree(submoduleEditCommit.getTree());
		walk.setFilter(TreeFilter.ANY_DIFF);
		List<DiffEntry> diffs = DiffEntry.scan(walk);
		assertEquals(1
		DiffEntry subDiff = diffs.get(0);
		assertEquals(FileMode.GITLINK
		assertEquals(FileMode.GITLINK
		assertEquals(commit2
		assertEquals(commit
		assertEquals(path
		assertEquals(path
	}

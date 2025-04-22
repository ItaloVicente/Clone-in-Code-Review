		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		addRepoToClose(repo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path, generator.getPath());
		assertEquals(commit, generator.getObjectId());
		assertEquals(uri, generator.getModulesUrl());
		assertEquals(path, generator.getModulesPath());
		assertEquals(uri, generator.getConfigUrl());
		Repository subModRepo = generator.getRepository();
		assertNotNull(subModRepo);
		subModRepo.close();
		assertEquals(commit, repo.resolve(Constants.HEAD));

		RevCommit submoduleCommit = git.commit().setMessage("submodule add")
				.setOnly(path).call();
		assertNotNull(submoduleCommit);
		TreeWalk walk = new TreeWalk(db);
		walk.addTree(commit.getTree());
		walk.addTree(submoduleCommit.getTree());
		walk.setFilter(TreeFilter.ANY_DIFF);
		List<DiffEntry> diffs = DiffEntry.scan(walk);
		assertEquals(1, diffs.size());
		DiffEntry subDiff = diffs.get(0);
		assertEquals(FileMode.MISSING, subDiff.getOldMode());
		assertEquals(FileMode.GITLINK, subDiff.getNewMode());
		assertEquals(ObjectId.zeroId(), subDiff.getOldId().toObjectId());
		assertEquals(commit, subDiff.getNewId().toObjectId());
		assertEquals(path, subDiff.getNewPath());

		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt", "content2");
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("edit file").call();

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
		assertEquals(commit2, generator.getObjectId());
		assertEquals(uri, generator.getModulesUrl());
		assertEquals(path, generator.getModulesPath());
		assertEquals(uri, generator.getConfigUrl());
		Repository subModRepo = generator.getRepository();
		assertNotNull(subModRepo);
		subModRepo.close();
		assertEquals(commit2, repo.resolve(Constants.HEAD));

		RevCommit submoduleAddCommit = git.commit().setMessage("submodule add")
				.setOnly(path).call();
		assertNotNull(submoduleAddCommit);

		RefUpdate update = repo.updateRef(Constants.HEAD);
		update.setNewObjectId(commit);
		assertEquals(Result.FORCED, update.forceUpdate());

		RevCommit submoduleEditCommit = git.commit()
				.setMessage("submodule add").setOnly(path).call();
		assertNotNull(submoduleEditCommit);
		TreeWalk walk = new TreeWalk(db);
		walk.addTree(submoduleAddCommit.getTree());
		walk.addTree(submoduleEditCommit.getTree());
		walk.setFilter(TreeFilter.ANY_DIFF);
		List<DiffEntry> diffs = DiffEntry.scan(walk);
		assertEquals(1, diffs.size());
		DiffEntry subDiff = diffs.get(0);
		assertEquals(FileMode.GITLINK, subDiff.getOldMode());
		assertEquals(FileMode.GITLINK, subDiff.getNewMode());
		assertEquals(commit2, subDiff.getOldId().toObjectId());
		assertEquals(commit, subDiff.getNewId().toObjectId());
		assertEquals(path, subDiff.getNewPath());
		assertEquals(path, subDiff.getOldPath());

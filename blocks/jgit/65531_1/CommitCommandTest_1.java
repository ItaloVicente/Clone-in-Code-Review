		try (Git git = new Git(db)) {
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
			addRepoToClose(repo);

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit2
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			Repository subModRepo = generator.getRepository();
			assertNotNull(subModRepo);
			subModRepo.close();
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
			try (TreeWalk walk = new TreeWalk(db)) {
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
		}

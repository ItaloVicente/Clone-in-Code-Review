		try (Git git = new Git(db)) {
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
			addRepoToClose(repo);

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			Repository subModRepo = generator.getRepository();
			assertNotNull(subModRepo);
			subModRepo.close();
			assertEquals(commit

			RevCommit submoduleCommit = git.commit().setMessage("submodule add")
					.setOnly(path).call();
			assertNotNull(submoduleCommit);
			try (TreeWalk walk = new TreeWalk(db)) {
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
		}

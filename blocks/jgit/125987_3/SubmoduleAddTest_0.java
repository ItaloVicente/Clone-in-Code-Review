			assertEquals(path
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
				assertEquals(subCommit
			}

			Status status = Git.wrap(db).status().call();
			assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
			assertTrue(status.getAdded().contains(path));
		}
	}

	@Test
	public void addSubmoduleWithName() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String name = "testsub";
			command.setName(name);
			String path = "sub";
			command.setPath(path);
			String uri = db.getDirectory().toURI().toString();
			command.setURI(uri);
			ObjectId subCommit;
			try (Repository repo = command.call()) {
				assertNotNull(repo);
				subCommit = repo.resolve(Constants.HEAD);
			}

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			generator.loadModulesConfig();
			assertTrue(generator.next());
			assertEquals(name

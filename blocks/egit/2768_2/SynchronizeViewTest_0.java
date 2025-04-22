	@Test public void shouldSynchronizeInLessThen11seconds() throws Exception {
		resetRepositoryToCreateInitialTag();
		File repoRoot = new File(getTestDirectory(), REPO1);
		FileRepository db = new FileRepository(new File(repoRoot, DOT_GIT));
		TestRepository<FileRepository> tr = new TestRepository<FileRepository>(db);
		DirCacheEntry file = tr.file("test.txt", tr.blob("first file"));
		RevCommit root = tr.commit(tr.tree(file));

		DirCacheEntry[] files = new DirCacheEntry[15];
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 15; j++)
				files[j] = tr.file("t" + (i + j), tr.blob("x" + (i + j)));

			tr.tick(5);
			root = tr.commit(tr.tree(files), root);
		}
		tr.update("master", root);

		long time = launchSynchronization(SynchronizeWithAction_tagsName,
				INITIAL_TAG, SynchronizeWithAction_localRepoName, HEAD, false);

		assertTrue(time < 11000);
	}


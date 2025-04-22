	@Test
	public void testCollectionWithGarbageCoalescenceWithShortTtl()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 100; i++) {
			mockSystemReader.tick(1);
			commit1 = commit().message("g" + i).parent(commit1).create();

			DfsGarbageCollector gc = new DfsGarbageCollector(repo);
			gc.setGarbageTtl(60
			run(gc);

			int count = countPacks(UNREACHABLE_GARBAGE);
			assertTrue("Garbage pack count should not exceed 4
					+ count
		}
	}

	@Test
	public void testCollectionWithGarbageCoalescenceWithLongTtl()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 100; i++) {
			mockSystemReader.tick(3600);
			commit1 = commit().message("g" + i).parent(commit1).create();

			DfsGarbageCollector gc = new DfsGarbageCollector(repo);
			gc.setGarbageTtl(2
			run(gc);

			int count = countPacks(UNREACHABLE_GARBAGE);
			assertTrue("Garbage pack count should not exceed 3
					+ count
		}
	}


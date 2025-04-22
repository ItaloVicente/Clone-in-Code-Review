	@Test
	public void testSinglePackForAllRefs() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		git.update("head"
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.getPackConfig().setSinglePack(true);
		run(gc);
		assertEquals(1

		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.getPackConfig().setSinglePack(false);
		run(gc);
		assertEquals(2
	}


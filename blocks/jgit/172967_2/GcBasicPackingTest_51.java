	@Theory
	public void testPack2Commits_noPackFolder(boolean aggressive) throws Exception {
		File packDir = repo.getObjectDatabase().getPackDirectory();
		assertTrue(packDir.delete());

		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(1
		assertEquals(2

		assertTrue(packDir.exists());
	}


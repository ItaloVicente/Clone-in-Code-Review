	@Test
	public void testPruneAndRestoreOldPacks() throws Exception {
		String tempRef = "refs/heads/soon-to-be-unreferenced";
		BranchBuilder bb = tr.branch(tempRef);
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0

		configureGc(gc
		gc.setExpireAgeMillis(0);
		gc.setPackExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1

		RefUpdate update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setForceUpdate(true);
		RefUpdate.Result result = update.delete();
		assertEquals(RefUpdate.Result.FORCED

		fsTick();

		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(0
		assertEquals(0

		update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setNewObjectId(objectId);
		update.setExpectedOldObjectId(null);
		result = update.update();
		assertEquals(RefUpdate.Result.NEW

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(1
	}


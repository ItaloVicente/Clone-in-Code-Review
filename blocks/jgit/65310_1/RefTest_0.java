
	@Test
	public void testDeleteRef() throws Exception {
		assertTrue(!db.isBare());
		RefUpdate update = db.updateRef(Constants.HEAD);
		assertEquals(Result.REJECTED_CURRENT_BRANCH

		Repository bareRepo = createBareRepository();
		update = bareRepo.updateRef(Constants.HEAD);
		assertEquals(Result.NEW
	}

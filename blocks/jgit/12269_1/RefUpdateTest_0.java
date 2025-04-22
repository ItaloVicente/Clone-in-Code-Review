	@Test
	public void testDeleteWithoutHead() throws IOException {
		RefUpdate refUpdate = db.updateRef(Constants.HEAD
		refUpdate.setForceUpdate(true);
		refUpdate.setNewObjectId(ObjectId.zeroId());
		Result updateResult = refUpdate.update();
		assertEquals(Result.FORCED
		Result deleteHeadResult = db.updateRef(Constants.HEAD).delete();
		assertEquals(Result.NO_CHANGE

		db.updateRef(Constants.R_HEADS + "master").delete();
	}


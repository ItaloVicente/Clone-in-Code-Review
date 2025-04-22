		Repository bareRepo = createBareRepository();
		RefUpdate ref = bareRepo.updateRef(Constants.HEAD);
		ref.setNewObjectId(ObjectId
				.fromString("0123456789012345678901234567890123456789"));
		assertEquals(Result.NEW
		ref = bareRepo.updateRef(Constants.HEAD);
		delete(bareRepo

		String master = "refs/heads/master";
		Ref head = bareRepo.exactRef(Constants.HEAD);
		assertNotNull(head);
		assertTrue(head.isSymbolic());
		assertEquals(master
		assertNull(head.getObjectId());
		assertNull(bareRepo.exactRef(master));

		ObjectId blobId;
		try (ObjectInserter ins = bareRepo.newObjectInserter()) {
			blobId = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}


		assertNotNull(trackingRefUpdate);
		assertEquals(trackingBranch, trackingRefUpdate.getLocalName());
		assertEquals(branch, trackingRefUpdate.getRemoteName());
		assertEquals(commit2.getId(), trackingRefUpdate.getNewObjectId());
		assertEquals(commit2.getId(), db.resolve(trackingBranch));
		assertEquals(commit2.getId(), db2.resolve(branch));

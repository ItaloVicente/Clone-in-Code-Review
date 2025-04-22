
		assertReflog(prevHead
	}

	private void assertReflog(ObjectId prevHead
			throws IOException {
		String actualHeadMessage = db.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment();
		String expectedHeadMessage = head.getName() + ": updating HEAD";
		assertEquals(expectedHeadMessage
		assertEquals(head.getName()
				.getLastEntry().getNewId().getName());
		assertEquals(prevHead.getName()
				.getReflogReader(Constants.HEAD).getLastEntry().getOldId()
				.getName());

		String actualMasterMessage = db.getReflogReader("refs/heads/master")
				.getLastEntry().getComment();
		assertEquals(expectedMasterMessage
		assertEquals(head.getName()
				.getLastEntry().getNewId().getName());
		assertEquals(prevHead.getName()
				db.getReflogReader("refs/heads/master").getLastEntry()
						.getOldId().getName());

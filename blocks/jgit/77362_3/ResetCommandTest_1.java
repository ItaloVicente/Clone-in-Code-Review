	private void assertReflogDisabled(ObjectId head)
			throws IOException {
		String actualHeadMessage = db.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment();
		String expectedHeadMessage = "commit: adding a.txt and dir/b.txt";
		assertEquals(expectedHeadMessage
		assertEquals(head.getName()
				.getLastEntry().getOldId().getName());

		String actualMasterMessage = db.getReflogReader("refs/heads/master")
				.getLastEntry().getComment();
		String expectedMasterMessage = "commit: adding a.txt and dir/b.txt";
		assertEquals(expectedMasterMessage
		assertEquals(head.getName()
				.getLastEntry().getOldId().getName());
	}

		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		assertEquals(8
		assertEquals("rebase: change file1 in topic"
				.getComment());
		assertEquals("checkout: moving from " + topicCommit.getName() + " to "
				+ lastMasterChange.getName()

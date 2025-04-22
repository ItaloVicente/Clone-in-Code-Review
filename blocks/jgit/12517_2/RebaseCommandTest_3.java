		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals(2
		assertEquals(3
		assertEquals("rebase finished: refs/heads/topic onto "
				+ lastMasterChange.getName()
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());

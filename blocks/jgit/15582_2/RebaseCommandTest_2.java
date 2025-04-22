
		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> sideLog = db.getReflogReader("refs/heads/side")
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
		assertEquals("rebase: update file2 on side"
				.getComment());
		assertEquals("rebase: Add file2"
		assertEquals("rebase: update file3 on topic"
				.getComment());
		assertEquals("checkout: moving from topic to " + b.getName()
				.get(4).getComment());
		assertEquals(2
		assertEquals(2
		assertEquals(5
		assertEquals("rebase finished: refs/heads/topic onto " + b.getName()
				topicLog.get(0).getComment());

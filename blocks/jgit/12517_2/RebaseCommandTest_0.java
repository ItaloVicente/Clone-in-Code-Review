
		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
		assertEquals("checkout: moving from topic to " + second.getName()
				headLog.get(1).getComment());
		assertEquals(2
		assertEquals(2
		assertEquals(
				"rebase finished: refs/heads/topic onto " + second.getName()
				topicLog.get(0).getComment());

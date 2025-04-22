		assertEquals("commit: initial commit"
				db
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("commit: initial commit"
				db
				.getReflogReader(db.getBranch()).getLastEntry().getComment());

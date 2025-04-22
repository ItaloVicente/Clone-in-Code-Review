		assertEquals("merge " + secondCommit.getId().getName()
				+ ": Merge made by resolve."
				.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment());
		assertEquals("merge " + secondCommit.getId().getName()
				+ ": Merge made by resolve."
				.getReflogReader(db.getBranch())
				.getLastEntry().getComment());

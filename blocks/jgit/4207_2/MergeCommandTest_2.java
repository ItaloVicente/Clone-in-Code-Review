		assertEquals(
				"merge refs/heads/master: Merge made by "
						+ mergeStrategy.getName() + "."
				db.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals(
				"merge refs/heads/master: Merge made by "
						+ mergeStrategy.getName() + "."
				db.getReflogReader(db.getBranch()).getLastEntry().getComment());

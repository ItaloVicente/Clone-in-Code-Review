
		if (reason == null) {
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("cherry-pick: "));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("cherry-pick: "));
		}

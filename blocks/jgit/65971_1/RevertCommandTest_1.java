			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
		}

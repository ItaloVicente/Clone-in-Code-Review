			assertEquals("commit: Squashed commit of the following:"
					db.getReflogReader(Constants.HEAD).getLastEntry()
							.getComment());
			assertEquals("commit: Squashed commit of the following:"
					db.getReflogReader(db.getBranch()).getLastEntry()
							.getComment());

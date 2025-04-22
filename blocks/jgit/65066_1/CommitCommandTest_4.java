			assertEquals(1
			assertNull(db.readSquashCommitMsg());
			assertEquals("commit: Squashed commit of the following:"
					.getReflogReader(Constants.HEAD).getLastEntry().getComment());
			assertEquals("commit: Squashed commit of the following:"
					.getReflogReader(db.getBranch()).getLastEntry().getComment());
		}

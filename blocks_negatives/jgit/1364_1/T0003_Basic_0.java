		assertEquals(c2.getAuthor(), rm2.getAuthor());
		assertEquals(c2.getCommitId(), rm2.getCommitId());
		assertEquals(c2.getMessage(), rm2.getMessage());
		assertEquals(c2.getTree().getTreeId(), rm2.getTree().getTreeId());
		assertEquals(1, rm2.getParentIds().length);
		assertEquals(c1.getCommitId(), rm2.getParentIds()[0]);

		final Commit c3 = new Commit(db);

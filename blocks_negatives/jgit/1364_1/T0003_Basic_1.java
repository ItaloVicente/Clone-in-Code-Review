		assertEquals(c3.getAuthor(), rm3.getAuthor());
		assertEquals(c3.getCommitId(), rm3.getCommitId());
		assertEquals(c3.getMessage(), rm3.getMessage());
		assertEquals(c3.getTree().getTreeId(), rm3.getTree().getTreeId());
		assertEquals(2, rm3.getParentIds().length);
		assertEquals(c1.getCommitId(), rm3.getParentIds()[0]);
		assertEquals(c2.getCommitId(), rm3.getParentIds()[1]);

		final Commit c4 = new Commit(db);

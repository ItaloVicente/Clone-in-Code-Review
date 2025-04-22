		assertEquals(c4.getAuthor(), rm4.getAuthor());
		assertEquals(c4.getCommitId(), rm4.getCommitId());
		assertEquals(c4.getMessage(), rm4.getMessage());
		assertEquals(c4.getTree().getTreeId(), rm4.getTree().getTreeId());
		assertEquals(3, rm4.getParentIds().length);
		assertEquals(c1.getCommitId(), rm4.getParentIds()[0]);
		assertEquals(c2.getCommitId(), rm4.getParentIds()[1]);
		assertEquals(c3.getCommitId(), rm4.getParentIds()[2]);

		Git git = new Git(db);

		Status stat = git.status().call();
		assertEquals(0, stat.getAdded().size());
		assertEquals(0, stat.getChanged().size());
		assertEquals(0, stat.getMissing().size());
		assertEquals(0, stat.getModified().size());
		assertEquals(0, stat.getRemoved().size());
		assertEquals(0, stat.getUntracked().size());

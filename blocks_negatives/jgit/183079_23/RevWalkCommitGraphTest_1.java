		assertCommitCntInGraph(4);

		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file1"),
				TreeFilter.ANY_DIFF));
		assertEquals(c3, rw.next());
		assertEquals(c1, rw.next());
		assertNull(rw.next());

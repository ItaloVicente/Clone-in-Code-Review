		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file2"),
				TreeFilter.ANY_DIFF));
		assertEquals(c4, rw.next());
		assertEquals(c2, rw.next());
		assertNull(rw.next());
	}

	@Test
	public void testCommitsWalk() throws Exception {
		RevCommit c1 = commit();
		branch(c1, "commits/1");
		RevCommit c2 = commit(c1);
		branch(c2, "commits/2");
		RevCommit c3 = commit(c2);
		branch(c3, "commits/3");

		testRevWalkBehavior("commits/1", "commits/3");
		assertCommitCntInGraph(0);

		writeCommitGraph();
		testRevWalkBehavior("commits/1", "commits/3");
		assertCommitCntInGraph(3);

		RevCommit c4 = commit(c1);
		RevCommit c5 = commit(c4);
		RevCommit c6 = commit(c1);
		RevCommit c7 = commit(c6);

		RevCommit m1 = commit(c2, c4);
		branch(m1, "merge/1");
		RevCommit m2 = commit(c4, c6);
		branch(m2, "merge/2");
		RevCommit m3 = commit(c3, c5, c7);
		branch(m3, "merge/3");

		/*
		 * <pre>
		 * current graph structure:
		 *
		 *    __M3___
		 *   /   |   \
		 *  3 M1 5 M2 7
		 *  |/  \|/  \|
		 *  2    4    6
		 *  |___/____/
		 *  1
		 * </pre>
		 */
		writeCommitGraph();
		assertCommitCntInGraph(10);
		testRevWalkBehavior("merge/1", "merge/2");
		testRevWalkBehavior("merge/1", "merge/3");
		testRevWalkBehavior("merge/2", "merge/3");

		RevCommit c8 = commit(m3);
		branch(c8, "commits/8");

		/*
		 * <pre>
		 * current graph structure:
		 *       8
		 *       |
		 *    __M3___
		 *   /   |   \
		 *  3 M1 5 M2 7
		 *  |/  \|/  \|
		 *  2    4    6
		 *  |___/____/
		 *  1
		 * </pre>
		 */
		testRevWalkBehavior("commits/8", "merge/1");
		testRevWalkBehavior("commits/8", "merge/2");

		writeCommitGraph();
		assertCommitCntInGraph(11);
		testRevWalkBehavior("commits/8", "merge/1");
		testRevWalkBehavior("commits/8", "merge/2");
	}

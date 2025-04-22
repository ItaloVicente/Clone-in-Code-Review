		if (expect <= 0) {
			assertNull(graph);
			return;
		}
		assertNotNull(graph);
		assertEquals(expect, graph.getCommitCnt());
	}

	void testRevWalkBehavior(String branch, String compare) throws Exception {
		rw.setTreeFilter(TreeFilter.ALL);
		rw.setRevFilter(RevFilter.MERGE_BASE);
		testGraphTwoModes(branch, compare);

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.TOPO);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.COMMIT_TIME_DESC);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);
	}

	void branch(RevCommit commit, String name) throws Exception {
		createBranch(commit, Constants.R_HEADS + name);

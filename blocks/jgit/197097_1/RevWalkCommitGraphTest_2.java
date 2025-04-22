
	void assertCommitCntInGraph(int expect) {
		rw.dispose();
		CommitGraph graph = rw.getCommitGraph();

		if (expect <= 0) {
			assertNull(graph);
			return;
		}
		assertNotNull(graph);
		assertEquals(expect
	}

	void testRevWalkBehavior(String branch
		rw.setTreeFilter(TreeFilter.ALL);
		rw.setRevFilter(RevFilter.MERGE_BASE);
		testGraphTwoModes(branch

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.TOPO);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.COMMIT_TIME_DESC);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);
	}

	void branch(RevCommit commit
		createBranch(commit
	}

	private void testGraphTwoModes(String branch) throws Exception {
		testGraphTwoModes(Collections.singleton(db.resolve(branch)));
	}

	private void testGraphTwoModes(String branch
			throws Exception {
		List<ObjectId> commits = new ArrayList<>();
		commits.add(db.resolve(branch));
		commits.add(db.resolve(compare));
		testGraphTwoModes(commits);
	}

	private void testGraphTwoModes(Collection<ObjectId> starts)
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withOutGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withOutGraph.add(commit);
		}

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withGraph.add(commit);
		}
		rw.dispose();

		assertEquals(withOutGraph.size()

		for (int i = 0; i < withGraph.size(); i++) {
			RevCommit expect = withOutGraph.get(i);
			RevCommit commit = withGraph.get(i);

			assertEquals(expect.getId()
			assertEquals(expect.getTree()
			assertEquals(expect.getCommitTime()
			assertArrayEquals(expect.getParents()
			assertArrayEquals(expect.getRawBuffer()
		}
	}

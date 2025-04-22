
	private void testGraphTwoModes(String branch) throws Exception {
		testGraphTwoModes(Collections.singleton(db.resolve(branch)));
	}

	private void testGraphTwoModes(String branch, String compare)
			throws Exception {
		List<ObjectId> commits = new ArrayList<>();
		commits.add(db.resolve(branch));
		commits.add(db.resolve(compare));
		testGraphTwoModes(commits);
	}

	private void testGraphTwoModes(Collection<ObjectId> starts)
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION, false);
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withOutGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withOutGraph.add(commit);
		}

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION, true);
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withGraph.add(commit);
		}
		rw.dispose();

		assertEquals(withOutGraph.size(), withGraph.size());

		for (int i = 0; i < withGraph.size(); i++) {
			RevCommit expect = withOutGraph.get(i);
			RevCommit commit = withGraph.get(i);

			assertEquals(expect.getId(), commit.getId());
			assertEquals(expect.getTree(), commit.getTree());
			assertEquals(expect.getCommitTime(), commit.getCommitTime());
			assertArrayEquals(expect.getParents(), commit.getParents());
			assertArrayEquals(expect.getRawBuffer(), commit.getRawBuffer());
		}
	}

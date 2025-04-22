
	@Test
	public void testTreeFilter() throws Exception {
		RevCommit c1 = commitFile("file1"
		RevCommit c2 = commitFile("file2"
		RevCommit c3 = commitFile("file1"
		RevCommit c4 = commitFile("file2"

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(4);

		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file1")
				TreeFilter.ANY_DIFF));
		assertEquals(c3
		assertEquals(c1
		assertNull(rw.next());

		rw.dispose();
		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file2")
				TreeFilter.ANY_DIFF));
		assertEquals(c4
		assertEquals(c2
		assertNull(rw.next());
	}

	@Test
	public void testWalkWithCommitMessageFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b = commitBuilder().parent(a)
				.message("The quick brown fox jumps over the lazy dog!")
				.create();
		RevCommit c = commitBuilder().parent(b).message("commit-c").create();
		branch(c

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(3);

		rw.setRevFilter(MessageRevFilter.create("quick brown fox jumps"));
		rw.markStart(c);
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testCommitsWalk() throws Exception {
		RevCommit c1 = commit();
		branch(c1
		RevCommit c2 = commit(c1);
		branch(c2
		RevCommit c3 = commit(c2);
		branch(c3

		enableAndWriteCommitGraph();
		testRevWalkBehavior("commits/1"
		assertCommitCntInGraph(3);

		RevCommit c4 = commit(c1);
		RevCommit c5 = commit(c4);
		RevCommit c6 = commit(c1);
		RevCommit c7 = commit(c6);

		RevCommit m1 = commit(c2
		branch(m1
		RevCommit m2 = commit(c4
		branch(m2
		RevCommit m3 = commit(c3
		branch(m3

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(10);
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/2"

		RevCommit c8 = commit(m3);
		branch(c8

		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(11);
		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"
	}

	void assertCommitCntInGraph(int expect) {
		rw.dispose();
		CommitGraph graph = rw.getCommitGraph().orElse(null);

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

	Ref branch(RevCommit commit
		return Git.wrap(db).branchCreate().setName(name)
				.setStartPoint(commit.name()).call();
	}

	void testGraphTwoModes(String branch) throws Exception {
		testGraphTwoModes(Collections.singleton(db.resolve(branch)));
	}

	void testGraphTwoModes(String branch
			throws Exception {
		List<ObjectId> commits = new ArrayList<>();
		commits.add(db.resolve(branch));
		commits.add(db.resolve(compare));
		testGraphTwoModes(commits);
	}

	void testGraphTwoModes(Collection<ObjectId> starts)
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withOutGraph = new ArrayList<>();

		assertTrue(rw.getCommitGraph().isEmpty());
		for (RevCommit commit : rw) {
			withOutGraph.add(commit);
		}

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withGraph = new ArrayList<>();

		assertTrue(rw.getCommitGraph().isPresent());
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

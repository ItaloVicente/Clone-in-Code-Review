	@Test
	public void testWriteBloomFilter() throws Exception {
		RevCommit c1 = commitFile("file1"
		RevCommit c2 = commitFile("file2"

		writeCommitGraph();
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		CommitGraph graph = rw.getCommitGraph();
		assertNotNull(graph);
		assertCommitCntInGraph(2);
		assertNull(graph.findBloomFilter(c1));
		assertNull(graph.findBloomFilter(c2));

		writeChangedPathCommitGraph();
		rw.dispose();
		graph = rw.getCommitGraph();
		assertCommitCntInGraph(2);
		assertNotNull(graph.findBloomFilter(c1));
		assertNotNull(graph.findBloomFilter(c2));
	}

	@Test
	public void testPathFilter_Simple() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION

		RevCommit a = commit(tree(file("src/d1/file"
		RevCommit b = commit(tree(file("src/d1/file"
		RevCommit c = commit(tree(file("src/d1/file"
		RevCommit d = commit(tree(file("src/d1/file"
		RevCommit e = commit(tree(file("src/d1/file"
		branch(e
		writeChangedPathCommitGraph();
		rw.dispose();
		rw.setRewriteParents(false);
		CommitGraph graph = rw.getCommitGraph();
		assertNotNull(graph);
		assertNotNull(graph.findBloomFilter(a));

		filter("src/d1/file");
		markStart(rw.lookupCommit(e));

		assertEquals(d.getId()
		assertEquals(c.getId()
		assertEquals(a.getId()
	}

	@Test
	public void testPathFilter_Group() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION

		RevCommit a = commit(tree(file("src/d1/file"
		RevCommit b = commit(tree(file("src/d1/file"
				file("src/README"
		RevCommit c = commit(tree(file("src/d1/file"
				file("src/README"
		RevCommit d = commit(tree(file("src/d1/file"
				file("src/README"
		RevCommit e = commit(tree(file("src/d1/file"
				file("src/README"
		branch(e
		writeChangedPathCommitGraph();
		rw.dispose();
		rw.setRewriteParents(false);
		CommitGraph graph = rw.getCommitGraph();
		assertNotNull(graph);
		assertNotNull(graph.findBloomFilter(a));

		filter("src/d1/file"
		markStart(rw.lookupCommit(e));

		assertEquals(d.getId()
		assertEquals(c.getId()
		assertEquals(b.getId()
		assertEquals(a.getId()
	}

	void filter(String... paths) {
		rw.setTreeFilter(AndTreeFilter.create(
				PathFilterGroup.createFromStrings(paths)
				TreeFilter.ANY_DIFF));
	}


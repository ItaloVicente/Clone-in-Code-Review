	@Test
	public void testGraphComputeChangedPaths() throws Exception {
		StoredConfig storedConfig = db.getConfig();
		storedConfig.setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		storedConfig.save();
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"

		writeCommitGraph(Collections.singleton(c));
		assertEquals(3
		assertNotNull(commitGraph.newBloomKey("test"));
		assertNotNull(commitGraph.findBloomFilter(a));
		assertNotNull(commitGraph.findBloomFilter(b));
		assertNotNull(commitGraph.findBloomFilter(c));

		storedConfig.setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		storedConfig.save();
		writeCommitGraph(Collections.singleton(c));
		assertEquals(3
		assertNull(commitGraph.newBloomKey("test"));
		assertNull(commitGraph.findBloomFilter(a));
		assertNull(commitGraph.findBloomFilter(b));
		assertNull(commitGraph.findBloomFilter(c));
	}

	@Test
	public void testGraphReadChangedPaths() throws Exception {
		StoredConfig storedConfig = db.getConfig();
		storedConfig.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		storedConfig.setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		storedConfig.setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		storedConfig.save();
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"

		GC gc = new GC(db);
		gc.writeCommitGraph(Collections.singleton(c));
		commitGraph = db.newObjectReader().getCommitGraph();

		assertNotNull(commitGraph);
		assertEquals(3
		assertNull(commitGraph.newBloomKey("test"));
		assertNull(commitGraph.findBloomFilter(a));
		assertNull(commitGraph.findBloomFilter(b));
		assertNull(commitGraph.findBloomFilter(c));
	}

	@Test
	public void testBloomFilters() throws Exception {
		StoredConfig storedConfig = db.getConfig();
		storedConfig.setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		storedConfig.save();
		RevCommit a = commit(
				tree(file("d/f"
		RevCommit b = commit(
				tree(file("d/f"
		RevCommit c = commit(
				tree(file("d/f"

		writeCommitGraph(Collections.singleton(c));
		assertEquals(3
		assertNotNull(commitGraph.newBloomKey("test"));
		ChangedPathFilter filter;

		filter = (ChangedPathFilter) commitGraph.findBloomFilter(a);
		assertTrue(filter.contains(commitGraph.newBloomKey("d/f")));
		assertTrue(filter.contains(commitGraph.newBloomKey("d")));
		assertTrue(filter.contains(commitGraph.newBloomKey("1.txt")));
		assertArrayEquals(filter.getData()
				createBloomFilterData(new String[] { "d/f"

		filter = (ChangedPathFilter) commitGraph.findBloomFilter(b);
		assertTrue(filter.contains(commitGraph.newBloomKey("1.txt")));
		assertArrayEquals(filter.getData()
				createBloomFilterData(new String[] { "1.txt" }));

		filter = (ChangedPathFilter) commitGraph.findBloomFilter(c);
		assertTrue(filter.contains(commitGraph.newBloomKey("d/f")));
		assertTrue(filter.contains(commitGraph.newBloomKey("d")));
		assertArrayEquals(filter.getData()
				createBloomFilterData(new String[] { "d/f"
	}

	private byte[] createBloomFilterData(String[] strings) {
		int filterLen = (strings.length * BLOOM_BITS_PER_ENTRY + Byte.SIZE - 1)
				/ Byte.SIZE;
		ChangedPathFilter filter = new ChangedPathFilter(filterLen
				BLOOM_KEY_NUM_HASHES);

		for (String str : strings) {
			BloomFilter.Key key = ChangedPathFilter.newBloomKey(str
					BLOOM_KEY_NUM_HASHES);
			filter.addKey(key);
		}
		return filter.getData();
	}


	@Test
	public void testWriterWithChangedPaths() throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"

		Set<ObjectId> wants = Collections.singleton(c);
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(config
		writer.setComputeChangedPaths(true);
		writer.prepareCommitGraph(m
		assertTrue(writer.isComputeChangedPaths());

		writer.writeCommitGraph(m
		byte[] data = os.toByteArray();
		assertTrue(writer.getTotalBloomFilterDataSize() > 0);
		assertTrue(data.length > 0);
		byte[] headers = new byte[8];
		System.arraycopy(data
		assertArrayEquals(new byte[] {'C'
	}

	@Test
	public void testWriterWithoutChangedPaths() throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"

		Set<ObjectId> wants = Collections.singleton(c);
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(config
		writer.setComputeChangedPaths(false);
		writer.prepareCommitGraph(m
		assertFalse(writer.isComputeChangedPaths());

		writer.writeCommitGraph(m
		byte[] data = os.toByteArray();
		assertEquals(0
		assertTrue(data.length > 0);
		byte[] headers = new byte[8];
		System.arraycopy(data
		assertArrayEquals(new byte[] {'C'
	}


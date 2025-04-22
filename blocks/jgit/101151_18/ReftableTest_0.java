	@SuppressWarnings("boxing")
	@Test
	public void indexScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.refIndexLevels() > 0);
		assertTrue(stats.refIndexSize() > 0);
		assertScan(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void indexSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.refIndexLevels() > 0);
		assertTrue(stats.refIndexSize() > 0);
		assertSeek(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void noIndexScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		byte[] table = write(refs);
		assertEquals(0
		assertEquals(0
		assertEquals(4
		assertEquals(table.length
		assertScan(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void noIndexSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		byte[] table = write(refs);
		assertEquals(0
		assertEquals(4
		assertSeek(refs
	}


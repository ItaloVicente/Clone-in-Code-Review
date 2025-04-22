	@SuppressWarnings("boxing")
	@Test
	public void indexScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.indexKeys() > 0);
		assertTrue(stats.indexSize() > 0);

		ReftableReader r = read(table);
		r.seekToFirstRef();
		for (Ref exp : refs) {
			assertTrue("has " + exp.getName()
			Ref act = r.getRef();
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
		}
		assertFalse(r.next());
	}

	@SuppressWarnings("boxing")
	@Test
	public void indexSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.indexKeys() > 0);
		assertTrue(stats.indexSize() > 0);

		for (Ref exp : refs) {
			ReftableReader r = seek(table
			assertTrue("has " + exp.getName()
			Ref act = r.getRef();
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
			assertFalse(r.next());
		}
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

		ReftableReader r = read(table);
		r.seekToFirstRef();
		for (Ref exp : refs) {
			assertTrue("has " + exp.getName()
			Ref act = r.getRef();
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
		}
		assertFalse(r.next());
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

		for (Ref exp : refs) {
			ReftableReader r = seek(table
			assertTrue("has " + exp.getName()
			Ref act = r.getRef();
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
			assertFalse(r.next());
		}
	}


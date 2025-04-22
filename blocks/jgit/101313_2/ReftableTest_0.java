	@Test
	public void withReflog() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter().begin(buffer);

		writer.writeRef(master);
		writer.writeRef(next);

		writer.writeLog(MASTER
		writer.writeLog(NEXT

		writer.finish();
		stats = writer.getStats();
		byte[] table = buffer.toByteArray();
		assertEquals(193

		ReftableReader r = read(table);
		r.seekToFirstRef();
		assertTrue(r.next());
		assertEquals(MASTER
		assertEquals(id(1)

		assertTrue(r.next());
		assertEquals(NEXT
		assertEquals(id(2)
		assertFalse(r.next());

		r.seekToFirstLog();
		assertTrue(r.next());
		assertEquals(MASTER

		assertTrue(r.next());
		assertEquals(NEXT
		assertFalse(r.next());
	}

	@SuppressWarnings("boxing")
	@Test
	public void logScan() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter();
		writer.setLogBlockSize(2048);
		writer.begin(buffer);

		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			Ref ref = ref(String.format("refs/heads/%03d"
			refs.add(ref);
			writer.writeRef(ref);
		}

		PersonIdent who = new PersonIdent("Log"
		for (Ref ref : refs) {
			writer.writeLog(ref.getName()
					ref.getObjectId()
		}
		writer.finish();
		stats = writer.getStats();
		assertTrue(stats.logBytes() > 4096);
		byte[] table = buffer.toByteArray();

		ReftableReader r = read(table);
		r.seekToFirstLog();
		for (Ref exp : refs) {
			assertTrue("has " + exp.getName()
			assertEquals(exp.getName()
			ReflogEntry entry = r.getReflogEntry();
			assertNotNull(entry);
			assertEquals(who
			assertEquals(ObjectId.zeroId()
			assertEquals(exp.getObjectId()
			assertEquals("create " + exp.getName()
		}
		assertFalse(r.next());
	}

	@Test

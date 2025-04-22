	@Test
	public void withReflogNoChain() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(buffer);

		writer.writeRef(master);
		writer.writeRef(next);

		writer.writeLog(MASTER
		writer.writeLog(NEXT

		writer.finish();
		byte[] table = buffer.toByteArray();
		assertEquals(245

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(id(1)

			assertTrue(rc.next());
			assertEquals(NEXT
			assertEquals(id(2)
			assertFalse(rc.next());
		}
		try (LogCursor lc = t.allLogs()) {
			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(NEXT
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(2)
			assertEquals(who
			assertEquals(msg

			assertFalse(lc.next());
		}
	}

	@Test
	public void withReflogChained() throws IOException {
		Ref master = ref(MASTER
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(3)
				.begin(buffer);

		writer.writeRef(master);
		writer.writeLog(MASTER
		writer.writeLog(MASTER
		writer.writeLog(MASTER

		writer.finish();
		byte[] table = buffer.toByteArray();
		assertEquals(220

		ReftableReader t = read(table);
		try (LogCursor lc = t.allLogs()) {
			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(3
			assertEquals(id(2)
			assertEquals(id(3)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(2
			assertEquals(id(1)
			assertEquals(id(2)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertFalse(lc.next());
		}
	}

	@Test
	public void onlyReflog() throws IOException {
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(buffer);
		writer.writeLog(MASTER
		writer.writeLog(NEXT
		writer.finish();
		byte[] table = buffer.toByteArray();
		stats = writer.getStats();
		assertEquals(170
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seek("refs/heads/")) {
			assertFalse(rc.next());
		}
		try (LogCursor lc = t.allLogs()) {
			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(NEXT
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(2)
			assertEquals(who
			assertEquals(msg

			assertFalse(lc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void logScan() throws IOException {
		ReftableConfig cfg = new ReftableConfig();
		cfg.setRefBlockSize(256);
		cfg.setLogBlockSize(2048);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(cfg);
		writer.setMinUpdateIndex(1).setMaxUpdateIndex(1).begin(buffer);

		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			Ref ref = ref(String.format("refs/heads/%03d"
			refs.add(ref);
			writer.writeRef(ref);
		}

		PersonIdent who = new PersonIdent("Log"
		for (Ref ref : refs) {
			writer.writeLog(ref.getName()
					ObjectId.zeroId()
					"create " + ref.getName());
		}
		writer.finish();
		stats = writer.getStats();
		assertTrue(stats.logBytes() > 4096);
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		try (LogCursor lc = t.allLogs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName()
				assertEquals(exp.getName()
				ReflogEntry entry = lc.getReflogEntry();
				assertNotNull(entry);
				assertEquals(who
				assertEquals(ObjectId.zeroId()
				assertEquals(exp.getObjectId()
				assertEquals("create " + exp.getName()
			}
			assertFalse(lc.next());
		}
	}

	@Test

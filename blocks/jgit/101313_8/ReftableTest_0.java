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
		assertEquals(215

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
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(NEXT
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
		cfg.setLogBlockSize(2048);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter();
		writer.setConfig(cfg).begin(buffer);

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

	@Test
	public void reflogReader() throws IOException {
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

		PersonIdent who1 = new PersonIdent("Log"
		writer.writeLog(MASTER
		PersonIdent who2 = new PersonIdent("Log"
		writer.writeLog(MASTER
		PersonIdent who3 = new PersonIdent("Log"
		writer.writeLog(MASTER

		writer.finish();
		byte[] table = buffer.toByteArray();

		ReentrantLock lock = new ReentrantLock();
		ReftableReader t = read(table);
		ReftableReflogReader rlr  = new ReftableReflogReader(lock

		assertEquals(rlr.getLastEntry().getWho()
		List<PersonIdent> all = rlr.getReverseEntries().stream().map(x -> x.getWho()).collect(Collectors.toList());
		List<PersonIdent> more = rlr.getReverseEntries(4).stream().map(x -> x.getWho()).collect(Collectors.toList());
		assertEquals(all.size()
		assertEquals(rlr.getReverseEntry(1).getWho()
		List<ReflogEntry> entries = rlr.getReverseEntries(2);
		assertEquals(entries.get(1).getWho()

		assertEquals(all
	}




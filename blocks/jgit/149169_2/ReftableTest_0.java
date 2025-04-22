	@Test
	public void allRefs() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableConfig cfg = new ReftableConfig();
		cfg.setRefBlockSize(1024);
		cfg.setLogBlockSize(1024);
		cfg.setAlignBlocks(true);
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.setConfig(cfg)
				.begin(buffer);
		PersonIdent who = new PersonIdent("Log"

		List<String> names = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			String name = new String(new char[220]).replace("\0"
			names.add(name);
			writer.writeRef(ref(name
		}

		writer.writeLog(MASTER
		writer.finish();
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		RefCursor c = t.allRefs();

		int j = 0;
		while (c.next()) {
			assertEquals(names.get(j)
			j++;
		}
	}


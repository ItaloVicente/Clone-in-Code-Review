	@Test
	public void hasObjMapRefs() throws IOException {
		ArrayList<Ref> refs = new ArrayList<>();
		refs.add(ref(MASTER
		byte[] table = write(refs);
		ReftableReader t = read(table);
		assertTrue(t.hasObjectMap());
	}

	@Test
	public void hasObjMapRefsSmallTable() throws IOException {
		ArrayList<Ref> refs = new ArrayList<>();
		ReftableConfig cfg = new ReftableConfig();
		cfg.setIndexObjects(false);
		refs.add(ref(MASTER
		byte[] table = write(refs);
		ReftableReader t = read(table);
		assertTrue(t.hasObjectMap());
	}

	@Test
	public void hasObjLogs() throws IOException {
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";
		ReftableConfig cfg = new ReftableConfig();
		cfg.setIndexObjects(false);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(buffer)
			.setMinUpdateIndex(1)
			.setConfig(cfg)
			.setMaxUpdateIndex(1)
			.begin();

		writer.writeLog("master"
		writer.finish();
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		assertTrue(t.hasObjectMap());
	}

	@Test
	public void hasObjMapRefsNoIndexObjects() throws IOException {
		ArrayList<Ref> refs = new ArrayList<>();
		ReftableConfig cfg = new ReftableConfig();
		cfg.setIndexObjects(false);
		cfg.setRefBlockSize(256);
		cfg.setAlignBlocks(true);

		int N = 256 * 5 / 25;
		for (int i= 0; i < N; i++) {
			@SuppressWarnings("boxing")
			Ref ref = ref(String.format("%02d/xxxxxxxxxx"
			refs.add(ref);
		}
		byte[] table = write(refs

		ReftableReader t = read(table);
		assertFalse(t.hasObjectMap());
	}


	@Test
	public void invalidRefWriteOrder() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(new ByteArrayOutputStream());

		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("records must be increasing");

		writer.writeRef(next);
		writer.writeRef(master);
	}

	@Test
	 public void invalidReflogWriteOrderUpdateIndex() throws IOException {
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(new ByteArrayOutputStream());
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("records must be increasing");

		writer.writeLog(MASTER
		writer.writeLog(MASTER
	}

	@Test
	public void invalidReflogWriteOrderName() throws IOException {
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(new ByteArrayOutputStream());
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("records must be increasing");
		writer.writeLog(NEXT
		writer.writeLog(MASTER
	}


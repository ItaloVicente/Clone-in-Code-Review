	@Test
	public void invalidRefWriteOrder() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(new ByteArrayOutputStream());

		writer.writeRef(next);
		IllegalArgumentException e  = assertThrows(
			IllegalArgumentException.class
			() -> writer.writeRef(master));
		assertThat(e.getMessage()
	}

	@Test
	public void invalidReflogWriteOrderUpdateIndex() throws IOException {
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(2)
			.begin(new ByteArrayOutputStream());
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		writer.writeLog(MASTER
		IllegalArgumentException e  = assertThrows(IllegalArgumentException.class
			() -> writer.writeLog(
				MASTER
		assertThat(e.getMessage()
	}

	@Test
	public void invalidReflogWriteOrderName() throws IOException {
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(new ByteArrayOutputStream());
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		writer.writeLog(NEXT
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class
			() -> writer.writeLog(
				MASTER
		assertThat(e.getMessage()
	}


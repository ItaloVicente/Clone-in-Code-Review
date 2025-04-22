	@Test
	public void reflogSeekPrefix() throws IOException {
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
			.setMinUpdateIndex(1)
			.setMaxUpdateIndex(1)
			.begin(buffer);

		writer.writeLog("branchname"
		writer.writeLog("branch"

		writer.finish();
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		try (LogCursor c = t.seekLog(MASTER
			assertTrue(c.next());
			assertEquals(c.getReflogEntry().getComment()
		}
	}



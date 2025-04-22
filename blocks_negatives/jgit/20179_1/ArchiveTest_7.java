				"git archive --format=zip HEAD", db);
		assertArrayEquals(l.toArray(new String[l.size()]),
				listZipEntries(result));
	}

	@Test
	public void testTarWithLongFilename() throws Exception {
		String filename = "";
		final List<String> l = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			filename = filename + "1234567890/";
			l.add(filename);

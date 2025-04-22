	@Test
	public void byObjectIdSkipPastPrefix() throws IOException {
		ReftableReader t = read(write());
		try (RefCursor rc = t.byObjectId(id(2))) {
			assertThrows(UnsupportedOperationException.class
		}
	}


	@Test
	public void byObjectIdSkipPastPrefix() throws IOException {
		ReftableReader t = read(write());
		try (RefCursor rc = t.byObjectId(id(2))) {
			assertThrows(UnsupportedOperationException.class
		}
	}

	@Test
	public void skipPastRefWithLastUTF8() throws IOException {
		ReftableReader t = read(write(ref(String.format("refs/heads/%sbla"

		try (RefCursor rc = t.allRefs()) {
			rc.seekPastPrefix("refs/heads/");
			assertFalse(rc.next());
		}
	}


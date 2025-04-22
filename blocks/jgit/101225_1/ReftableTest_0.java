	@Test
	public void emptyList() throws IOException {
		RefCursor rc = RefCursor.from(Collections.emptyList());
		rc.seekToFirstRef();
		assertFalse(rc.next());

		rc.seek(HEAD);
		assertFalse(rc.next());
	}


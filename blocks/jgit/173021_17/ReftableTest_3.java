	@Test
	public void skipPastRefWithLastUTF8() throws IOException {
		ReftableReader t = read(write(ref(String.format("refs/heads/%sbla"
				

		try (RefCursor rc = t.allRefs()) {
			rc.seekPastPrefix("refs/heads/");
			assertFalse(rc.next());
		}
	}



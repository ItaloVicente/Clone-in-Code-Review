	@Test
	public void byObjectIdSkipPastPrefix() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 200; i++) {
			@SuppressWarnings("boxing")
			Ref ref = ref(String.format("refs/heads/%02d"
			refs.add(ref);
		}
		refs.add(ref("refs/heads/master"

		ReftableReader t = read(write(refs));
		assertEquals(0

		try (RefCursor rc = t.byObjectId(id(42))) {
			rc.seekPastPrefix("refs/heads/4");
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.byObjectId(id(100))) {
			rc.seekPastPrefix("refs/heads/1");
			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(100)
			assertEquals(0

			assertFalse(rc.next());
		}
	}


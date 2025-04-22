	@SuppressWarnings("boxing")
	@Test
	public void byObjectIdOneRefNoIndex() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 200; i++) {
			refs.add(ref(String.format("refs/heads/%02d"
		}
		refs.add(ref("refs/heads/master"

		ReftableReader t = read(write(refs));
		assertEquals(2
		assertEquals(0

		try (RefCursor rc = t.byObjectId(id(42))) {
			assertTrue("has 42"
			assertEquals("refs/heads/42"
			assertEquals(id(42)
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.byObjectId(id(100))) {
			assertTrue("has 100"
			assertEquals("refs/heads/100"
			assertEquals(id(100)

			assertTrue("has master"
			assertEquals("refs/heads/master"
			assertEquals(id(100)

			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void byObjectIdOneRefWithIndex() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5200; i++) {
			refs.add(ref(String.format("refs/heads/%02d"
		}
		refs.add(ref("refs/heads/master"

		ReftableReader t = read(write(refs));
		assertTrue(stats.objIndexSize() > 0);

		try (RefCursor rc = t.byObjectId(id(42))) {
			assertTrue("has 42"
			assertEquals("refs/heads/42"
			assertEquals(id(42)
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.byObjectId(id(100))) {
			assertTrue("has 100"
			assertEquals("refs/heads/100"
			assertEquals(id(100)

			assertTrue("has master"
			assertEquals("refs/heads/master"
			assertEquals(id(100)

			assertFalse(rc.next());
		}
	}


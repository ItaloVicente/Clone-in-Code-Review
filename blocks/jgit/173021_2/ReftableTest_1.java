	@Test
	public void seekPastRef() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		ReftableReader t = read(write(exp
		try (RefCursor rc = t.seekPastRef("refs/heads/next/")) {
			assertTrue(rc.next());
			assertEquals(AFTER_NEXT

			assertTrue(rc.next());
			assertEquals(LAST

			assertFalse(rc.next());
		}
	}


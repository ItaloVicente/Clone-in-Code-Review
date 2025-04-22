	@Test
	public void seekPastRefWithRefCursor() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		ReftableReader t = read(write(exp
		try (RefCursor rc = t.seekRefsWithPrefix("")) {
			assertTrue(rc.next());
			assertEquals(MASTER

			rc.seekPastPrefix("refs/heads/next/");

			assertTrue(rc.next());
			assertEquals(AFTER_NEXT
			assertTrue(rc.next());
			assertEquals(LAST

			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastToNonExistentPrefixToTheMiddle() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		ReftableReader t = read(write(exp
		try (RefCursor rc = t.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/master_non_existent");

			assertTrue(rc.next());
			assertEquals(NEXT

			assertTrue(rc.next());
			assertEquals(AFTER_NEXT

			assertTrue(rc.next());
			assertEquals(LAST

			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastToNonExistentPrefixToTheEnd() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		ReftableReader t = read(write(exp
		try (RefCursor rc = t.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/nextnon_existent_end");
			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastWithSeekRefsWithPrefix() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		Ref notRefsHeads = ref(NOT_REF_HEADS
		ReftableReader t = read(write(exp
		try (RefCursor rc = t.seekRefsWithPrefix("refs/heads/")) {
			rc.seekPastPrefix("refs/heads/next/");
			assertTrue(rc.next());
			assertEquals(AFTER_NEXT
			assertTrue(rc.next());
			assertEquals(LAST

			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastWithLotsOfRefs() throws IOException {
		Ref[] refs = new Ref[500];
		for (int i = 1; i <= 500; i++) {
			refs[i - 1] = ref(String.format("refs/%d"
		}
		ReftableReader t = read(write(refs));
		try (RefCursor rc = t.allRefs()) {
			rc.seekPastPrefix("refs/3");
			assertTrue(rc.next());
			assertEquals("refs/4"
			assertTrue(rc.next());
			assertEquals("refs/40"

			rc.seekPastPrefix("refs/8");
			assertTrue(rc.next());
			assertEquals("refs/9"
			assertTrue(rc.next());
			assertEquals("refs/90"
			assertTrue(rc.next());
			assertEquals("refs/91"
		}
	}

	@Test
	public void seekPastManyTimes() throws IOException {
		Ref exp = ref(MASTER
		Ref next = ref(NEXT
		Ref afterNext = ref(AFTER_NEXT
		Ref afterNextNext = ref(LAST
		ReftableReader t = read(write(exp

		try (RefCursor rc = t.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/master");
			rc.seekPastPrefix("refs/heads/next");
			rc.seekPastPrefix("refs/heads/nextnext");
			rc.seekPastPrefix("refs/heads/nextnextnext");
			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastOnEmptyTable() throws IOException {
		ReftableReader t = read(write());
		try (RefCursor rc = t.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/");
			assertFalse(rc.next());
		}
	}


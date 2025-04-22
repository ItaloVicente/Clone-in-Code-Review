	@Test
	public void twoTableSeekPastWithRefCursor() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/banana"
				ref("refs/heads/zzlast"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekRefsWithPrefix("")) {
			assertTrue(rc.next());
			assertEquals("refs/heads/apple"
			assertEquals(id(1)

			rc.seekPastPrefix("refs/heads/banana/");

			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)

			assertTrue(rc.next());
			assertEquals("refs/heads/zzlast"
			assertEquals(id(4)

			assertEquals(1
		}
	}

	@Test
	public void oneTableSeekPastWithRefCursor() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"

		MergedReftable mr = merge(write(delta1));
		try (RefCursor rc = mr.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/apple");

			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)

			assertEquals(1
		}
	}

	@Test
	public void seekPastToNonExistentPrefixToTheMiddle() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/banana"
				ref("refs/heads/zzlast"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/x");

			assertTrue(rc.next());
			assertEquals("refs/heads/zzlast"
			assertEquals(id(4)

			assertEquals(1
		}
	}

	@Test
	public void seekPastToNonExistentPrefixToTheEnd() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/banana"
				ref("refs/heads/zzlast"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/zzz");
			assertFalse(rc.next());
		}
	}

	@Test
	public void seekPastManyTimes() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/banana"
				ref("refs/heads/zzlast"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekRefsWithPrefix("")) {
			rc.seekPastPrefix("refs/heads/apple");
			rc.seekPastPrefix("refs/heads/banana");
			rc.seekPastPrefix("refs/heads/master");
			rc.seekPastPrefix("refs/heads/zzlast");
			assertFalse(rc.next());
		}
	}


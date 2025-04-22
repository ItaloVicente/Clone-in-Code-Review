	@Test
	public void namespaceNotFound() throws IOException {
		Ref exp = ref(MASTER
		ReftableReader t = read(write(exp));
		try (RefCursor rc = t.seek("refs/changes/")) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seek("refs/tags/")) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void namespaceHeads() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		Ref v1 = tag(V1_0

		ReftableReader t = read(write(master
		try (RefCursor rc = t.seek("refs/tags/")) {
			assertTrue(rc.next());
			assertEquals(V1_0
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seek("refs/heads/")) {
			assertTrue(rc.next());
			assertEquals(MASTER

			assertTrue(rc.next());
			assertEquals(NEXT

			assertFalse(rc.next());
		}
	}


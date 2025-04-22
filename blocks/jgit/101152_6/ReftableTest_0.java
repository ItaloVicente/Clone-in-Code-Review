	@Test
	public void namespaceNotFound() throws IOException {
		Ref exp = ref(MASTER
		ReftableReader r = read(write(exp));
		r.seek("refs/changes/");
		assertFalse(r.next());

		r.seek("refs/tags/");
		assertFalse(r.next());
	}

	@Test
	public void namespaceHeads() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		Ref v1 = tag(V1_0

		ReftableReader r = read(write(master
		r.seek("refs/tags/");
		assertTrue(r.next());
		assertEquals(V1_0
		assertFalse(r.next());

		r.seek("refs/heads/");
		assertTrue(r.next());
		assertEquals(MASTER

		assertTrue(r.next());
		assertEquals(NEXT

		assertFalse(r.next());
	}


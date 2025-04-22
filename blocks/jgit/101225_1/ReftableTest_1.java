	@Test
	public void namespacesFromList() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		Ref v1 = tag(V1_0

		RefCursor r = RefCursor.from(Arrays.asList(v1
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


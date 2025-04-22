	@Test
	public void resolveSymbolicRef() throws IOException {
		Reftable t = read(write(
				sym(HEAD
				sym("refs/heads/tmp"
				ref(MASTER

		Ref head = t.exactRef(HEAD);
		assertNull(head.getObjectId());
		assertEquals("refs/heads/tmp"

		head = t.resolve(head);
		assertNotNull(head);
		assertEquals(id(1)
	}

	@Test
	public void failChainOfSymbolicRef() throws IOException {
		Reftable t = read(write(
				sym(HEAD
				sym("refs/heads/1"
				sym("refs/heads/2"
				sym("refs/heads/3"
				sym("refs/heads/4"
				sym("refs/heads/5"
				ref(MASTER

		Ref head = t.exactRef(HEAD);
		assertNull(head.getObjectId());
		assertNull(t.resolve(head));
	}


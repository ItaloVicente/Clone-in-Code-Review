	@Test
	public void testGetRefsByPrefix_HeadShouldLoadSingleRef()
			throws IOException {
		List<Ref> headRefs;
		Ref head;

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/branch"

		headRefs = refdir.getRefsByPrefix(HEAD);
		assertEquals(1
		assertNull(loadedRefsByPrefix);

		head = headRefs.get(0);

		assertEquals(HEAD
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		assertEquals(A
	}


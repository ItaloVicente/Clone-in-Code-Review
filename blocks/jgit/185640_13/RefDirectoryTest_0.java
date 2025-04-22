	@Test
	public void testGetRefs_HeadShouldReturnSingleRef()
			throws IOException {
		Map<String
		Ref head;

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/branch"

		all = refdir.getRefs(HEAD);
		assertEquals(1
		assertTrue("has HEAD"

		head = all.get(HEAD);
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		assertEquals(A
	}


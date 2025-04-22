	@Test
	public void testGetRefs_LooseSortedCorrectly() throws IOException {
		Map<String

		writeLooseRef("refs/heads/project1/A"
		writeLooseRef("refs/heads/project1-B"

		refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertEquals(A
		assertEquals(B
	}


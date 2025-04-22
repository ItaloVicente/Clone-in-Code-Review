
	@Test
	public void testGetRefsByPrefixes() throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefsByPrefix();
		assertEquals(0

		refs = db.getRefDatabase().getRefsByPrefix("refs/heads/p"
				"refs/tags/A");
		assertEquals(3
		checkContainsRef(refs
		checkContainsRef(refs
		checkContainsRef(refs
	}

	@Test
	public void testGetRefsExcludingPrefix() throws IOException {
		Set<String> prefixes = new HashSet<>();
		prefixes.add("refs/tags");
		List<Ref> refs = db.getRefDatabase().getRefsExcludingPrefixes(prefixes);
		assertEquals(13
		checkContainsRef(refs
		checkContainsRef(refs
		for (Ref notInResult : db.getRefDatabase().getRefsByPrefix("refs/tags")) {
			assertFalse(refs.contains(notInResult));
		}
	}

	@Test
	public void testGetRefsExcludingPrefixes() throws IOException {
		Set<String> prefixes = new HashSet<>();
		prefixes.add("refs/tags/");
		prefixes.add("refs/heads/");
		List<Ref> refs = db.getRefDatabase().getRefsExcludingPrefixes(prefixes);
		assertEquals(1
		checkContainsRef(refs
	}


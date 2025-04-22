	@Test
	public void testGetRefsExcludingPrefix() throws IOException {
		Set<String> prefixes = new HashSet<>();
		prefixes.add("refs/tags");
		List<Ref> refs =
				db.getRefDatabase().getRefsByPrefixWithExclusions(RefDatabase.ALL
		assertEquals(13
		checkContainsRef(refs
		checkContainsRef(refs
		for (Ref notInResult : db.getRefDatabase().getRefsByPrefix("refs/tags")) {
			assertFalse(refs.contains(notInResult));
		}
	}

	@Test
	public void testGetRefsExcludingPrefixes() throws IOException {
		Set<String> exclude = new HashSet<>();
		exclude.add("refs/tags/");
		exclude.add("refs/heads/");
		List<Ref> refs = db.getRefDatabase().getRefsByPrefixWithExclusions(RefDatabase.ALL
		assertEquals(1
		checkContainsRef(refs
	}

	@Test
	public void testGetRefsExcludingNonExistingPrefixes() throws IOException {
		Set<String> exclude = new HashSet<>();
		exclude.add("refs/tags/");
		exclude.add("refs/heads/");
		exclude.add("refs/nonexistent/");
		List<Ref> refs = db.getRefDatabase().getRefsByPrefixWithExclusions(RefDatabase.ALL
		assertEquals(1
		checkContainsRef(refs
	}

	@Test
	public void testGetRefsWithPrefixExcludingPrefixes() throws IOException {
		Set<String> exclude = new HashSet<>();
		exclude.add("refs/heads/pa");
		String include = "refs/heads/p";
		List<Ref> refs = db.getRefDatabase().getRefsByPrefixWithExclusions(include
		assertEquals(1
		checkContainsRef(refs
	}

	@Test
	public void testGetRefsWithPrefixExcludingOverlappingPrefixes() throws IOException {
		Set<String> exclude = new HashSet<>();
		exclude.add("refs/heads/pa");
		exclude.add("refs/heads/");
		exclude.add("refs/heads/p");
		exclude.add("refs/tags/");
		List<Ref> refs = db.getRefDatabase().getRefsByPrefixWithExclusions(RefDatabase.ALL
		assertEquals(1
		checkContainsRef(refs
	}


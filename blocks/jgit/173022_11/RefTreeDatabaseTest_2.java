	@Test
	public void testGetRefsExcludingPrefix_WithoutTags() throws IOException {
		update("refs/heads/A"
		update("refs/heads/B"
		update("refs/tags/v1.0"
		update("refs/tagsnot/something"
		Set<String> toExclude = new HashSet<>();
		toExclude.add("refs/tags/");
		List<Ref> refs = refdb.getRefsByPrefixWithSkips(toExclude
		assertEquals(3
		assertTrue(refs.contains(refdb.exactRef("refs/heads/A")));
		assertTrue(refs.contains(refdb.exactRef("refs/heads/B")));
		assertTrue(refs.contains(refdb.exactRef("refs/tagsnot/something")));
	}


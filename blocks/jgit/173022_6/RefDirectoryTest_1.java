	@Test
	public void testGetRefs_ExcludingPrefixes() throws IOException {
		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/heads/B"
		writeLooseRef("refs/tags/tag"
		writeLooseRef("refs/something/something"
		writeLooseRef("refs/aaa/aaa"

		Set<String> toExclude = new HashSet<>();
		toExclude.add("refs/aaa/");
		toExclude.add("refs/heads/");
		List<Ref> refs = refdir.getRefsExcludingPrefixes(toExclude);

		assertEquals(2
		assertTrue(refs.contains(refdir.exactRef("refs/tags/tag")));
		assertTrue(refs.contains(refdir.exactRef("refs/something/something")));
	}


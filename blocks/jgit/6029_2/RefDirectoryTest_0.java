	@Test
	public void testGetRefsCaseSensitive() throws IOException {
		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/tags/lowercase"

		assertEquals(v1_0
		assertNull("case sensitive getRef shouldn't find wrong case"
				refdir.getRef("refs/tags/LOWERCASE"));
	}


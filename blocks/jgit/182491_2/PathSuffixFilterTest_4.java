	@Test
	public void testNegated() throws IOException {
		ObjectId treeId = createTree("a.sth"
				"sub/b.txt"

		List<String> paths = getMatchingPaths(".txt"
		List<String> expected = Arrays.asList("a.sth"

		assertEquals(expected
	}


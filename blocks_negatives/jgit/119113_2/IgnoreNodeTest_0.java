	@SuppressWarnings("deprecation")
	@Test
	public void testEmptyIgnoreNode() {
		IgnoreNode node = new IgnoreNode();
		assertEquals(MatchResult.CHECK_PARENT, node.isIgnored("", false));
		assertEquals(MatchResult.CHECK_PARENT, node.isIgnored("", false, false));
		assertEquals(MatchResult.CHECK_PARENT_NEGATE_FIRST_MATCH,
				node.isIgnored("", false, true));
	}


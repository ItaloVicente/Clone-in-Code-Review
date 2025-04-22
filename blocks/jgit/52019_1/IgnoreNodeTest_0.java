	@Test
	public void testEmptyIgnoreRules() throws IOException {
		IgnoreNode node = new IgnoreNode();
		node.parse(writeToString(""
		assertEquals(new ArrayList<>()
		node.parse(writeToString(" "
		assertEquals(2
	}


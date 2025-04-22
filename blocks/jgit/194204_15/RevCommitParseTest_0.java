
	@Test
	public void testParse_NoOverriddenParents() throws Exception {
		final RevCommit c = create("a message");
		assertFalse(c.hasOverriddenParents());
	}


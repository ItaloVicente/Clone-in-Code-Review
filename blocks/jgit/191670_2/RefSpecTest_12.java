
	@Test
	public void matching() {
		RefSpec a = new RefSpec(":");
		assertTrue(a.isMatching());
		assertFalse(a.isForceUpdate());
	}

	@Test
	public void matchingForced() {
		RefSpec a = new RefSpec("+:");
		assertTrue(a.isMatching());
		assertTrue(a.isForceUpdate());
	}

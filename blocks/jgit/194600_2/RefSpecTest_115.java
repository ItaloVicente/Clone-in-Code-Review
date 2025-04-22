
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

	@Test
	public void negativeRefSpecWithDest() {
		assertTrue(a.isNegative());
		assertNull(a.getSource());
		assertEquals(a.getDestination()
	}

	@Test
	public void negativeRefSpecWithSrcAndNullDest() {
		assertTrue(a.isNegative());
		assertNull(a.getDestination());
		assertEquals(a.getSource()
	}

	@Test
	public void negativeRefSpecWithSrcAndEmptyDest() {
		assertTrue(a.isNegative());
		assertTrue(a.getDestination().isEmpty());
		assertEquals(a.getSource()
	}

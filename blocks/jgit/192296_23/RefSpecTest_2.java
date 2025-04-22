
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

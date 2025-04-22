	@Test
	public void testEquals_Short4() {
		final String s = "7b6e";
		final AbbreviatedObjectId a = AbbreviatedObjectId.fromString(s);
		final AbbreviatedObjectId b = AbbreviatedObjectId.fromString(s);
		assertNotSame(a
		assertTrue(a.hashCode() != 0);
		assertTrue(a.hashCode() == b.hashCode());
		assertEquals(b
		assertEquals(a
	}


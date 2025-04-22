	@Test
	public void testAppendSanitized() {
		StringBuilder r = new StringBuilder();
		PersonIdent.appendSanitized(r
		assertEquals("Baz\u1234Quux"
	}

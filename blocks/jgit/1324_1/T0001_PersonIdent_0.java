
	public void test008_ParseIdent() {
		final String i = "A U Thor <author@example.com>
		final PersonIdent p = new PersonIdent(i);
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(1142878501000L
	}

	public void test009_ParseIdent() {
		final String i = "A U Thor<author@example.com>";
		final PersonIdent p = new PersonIdent(i);
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(0L
	}

	public void test010_ParseIdent() {
		final String i = "A U Thor<author@example.com> 1142878501 ";
		final PersonIdent p = new PersonIdent(i);
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(0L
	}

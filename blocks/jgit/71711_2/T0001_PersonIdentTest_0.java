	@Test
	public void testToExternalStringTrimsAllWhitespace() {
		String ws = "  \u0001 \n ";
		PersonIdent personIdent = new PersonIdent(ws
		assertEquals(ws
		assertEquals(ws

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith(" <>"));
	}

	@Test
	public void testToExternalStringTrimsOtherBadCharacters() {
		String name = " Foo\r\n<Bar> ";
		String email = " Baz>\n\u1234<Quux ";
		PersonIdent personIdent = new PersonIdent(name
		assertEquals(name
		assertEquals(email

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith("Foo\rBar <Baz\u1234Quux>"));
	}

	@Test
	public void testEmptyNameAndEmail() {
		PersonIdent personIdent = new PersonIdent(""
		assertEquals(""
		assertEquals(""

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith(" <>"));
	}


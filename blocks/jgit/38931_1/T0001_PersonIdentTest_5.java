
	@Test
	public void testToExternalStringTrimsNameAndEmail() throws Exception {
		PersonIdent personIdent = new PersonIdent("  A U Thor  "
				"  author@example.com  ");

		String externalString = personIdent.toExternalString();

		assertTrue(externalString.startsWith("A U Thor <author@example.com>"));
	}


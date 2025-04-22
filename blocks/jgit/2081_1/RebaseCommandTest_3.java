	public void testAuthorScriptConverter() throws Exception {
		PersonIdent ident = new PersonIdent("Author name"
				123456789123L
		String convertedAuthor = git.rebase().toAuthorScript(ident);
		String[] lines = convertedAuthor.split("\n");
		assertEquals("GIT_AUTHOR_NAME='Author name'"
		assertEquals("GIT_AUTHOR_EMAIL='a.mail@some.com'"
		assertEquals("GIT_AUTHOR_DATE='123456789 -0100'"

		PersonIdent parsedIdent = git.rebase().parseAuthor(
				convertedAuthor.getBytes("UTF-8"));
		assertEquals(ident.getName()
		assertEquals(ident.getEmailAddress()
		assertEquals(123456789000L
		assertEquals(ident.getTimeZoneOffset()

		ident = new PersonIdent("Author name"
				123456789123L
		convertedAuthor = git.rebase().toAuthorScript(ident);
		lines = convertedAuthor.split("\n");
		assertEquals("GIT_AUTHOR_NAME='Author name'"
		assertEquals("GIT_AUTHOR_EMAIL='a.mail@some.com'"
		assertEquals("GIT_AUTHOR_DATE='123456789 +0930'"

		parsedIdent = git.rebase().parseAuthor(
				convertedAuthor.getBytes("UTF-8"));
		assertEquals(ident.getName()
		assertEquals(ident.getEmailAddress()
		assertEquals(123456789000L
		assertEquals(ident.getTimeZoneOffset()
	}

	public void testRepositoryStateChecks() throws Exception {
		try {
			git.rebase().setOperation(Operation.ABORT).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
		try {
			git.rebase().setOperation(Operation.SKIP).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
		try {
			git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
	}


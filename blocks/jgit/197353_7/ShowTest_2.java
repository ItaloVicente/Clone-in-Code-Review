
	@Test
	public void testShowRaw() throws Exception {
		String result = toString(execute("git show --raw"));
		assertEquals(toString("commit ecdf62e777b7413fc463c20e935403d424410ab2"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				":100644 100644 2e65efe2a 59ef8d134 M\ta")
	}

	@Test
	public void testShowRawNoAbbrev() throws Exception {
		String result = toString(execute("git show --raw --no-abbrev"));
		assertEquals(toString("commit ecdf62e777b7413fc463c20e935403d424410ab2"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				":100644 100644 2e65efe2a145dda7ee51d1741299f848e5bf752e 59ef8d134f97de87ebcac8e3a0c32d78c81e842e M\ta")
				
	}

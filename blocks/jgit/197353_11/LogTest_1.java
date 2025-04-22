
	@Test
	public void testLogRaw() throws Exception {
		String result = toString(execute("git log --raw"));
		assertEquals(
				toString("commit b4680f542095a8b41ea4258a5c03b548543a817c"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						":000000 100644 000000000 2e65efe2a A\ta"
						":000000 100644 000000000 2e65efe2a A\tb")
				result);
	}

	@Test
	public void testLogRawNoAbbrev() throws Exception {
		String result = toString(execute("git log --raw --no-abbrev"));
		assertEquals(
				toString("commit b4680f542095a8b41ea4258a5c03b548543a817c"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						":000000 100644 0000000000000000000000000000000000000000 2e65efe2a145dda7ee51d1741299f848e5bf752e A\ta"
						":000000 100644 0000000000000000000000000000000000000000 2e65efe2a145dda7ee51d1741299f848e5bf752e A\tb")
				result);
	}

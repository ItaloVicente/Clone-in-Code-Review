	public void testParsePersonIdent_malformedCases()
			throws UnsupportedEncodingException {
		assertPersonIdent("Me me@example.com> 1234567890 -0700", 0, null);
		assertPersonIdent("Me <me@example.com 1234567890 -0700", 0, null);

		assertPersonIdent("<>", 0, null);
		assertPersonIdent("<me@example.com>", 0, null);
		assertPersonIdent(" <>", 0, null);
		assertPersonIdent(" <me@example.com>", 0, null);
		assertPersonIdent("Me <>", 0, null);
		assertPersonIdent("Me <me@example.com>", 0, null);

		assertPersonIdent("Me <me@example.com> 1234567890", 0, null);
		assertPersonIdent("<me@example.com> 1234567890 -0700", 0, null);
		assertPersonIdent("<> 1234567890 -0700", 0, null);
